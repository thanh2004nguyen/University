package com.aptech.group3.Controller;



import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Paymenttt;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.PaymentService;
import com.aptech.group3.service.PaypalService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.UserService;
import com.aptech.group3.service.VNPayService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller

@Slf4j
public class PaypalController {
	
	
	@Autowired
	private StudentClassService service;
	@Autowired
	private PaymentService paymentservice;
	 @Autowired
	    private VNPayService vnPayService;
	 @Autowired
	 private UserService userservice;
	 @Autowired
	 private ClassForSubjectService classforsubjectservice;
	@Autowired
    private PaypalService paypalService;
	@GetMapping("/paypal/indexx")
	public String home(HttpServletRequest request, Model model) {
	    String amountt = request.getParameter("amount");
	    String selectedItems = request.getParameter("selectedItems");

	    if (amountt != null) {
	        try {
	            int amount = Integer.parseInt(amountt);
	            model.addAttribute("amount", amount);
	        } catch (NumberFormatException e) {
	            model.addAttribute("amount", 0); // Or handle the error as needed
	        }
	    } else {
	        model.addAttribute("amount", 0); // Default value or handle as needed
	    }

	    if (selectedItems != null) {
	        model.addAttribute("selectedItems", selectedItems);
	    } else {
	        model.addAttribute("selectedItems", ""); // Default value or handle as needed
	    }

	    return "/page/paypal/indexx";
	}

	@PostMapping("/paypal/indexx")
	public String handlePaypalPayment(@RequestParam("amount") String amount,
	                                  @RequestParam("selectedItems") String selectedItems,
	                                  Model model) {
	    // Log the received amount and selected items
	    System.out.println("Received amount: " + amount);
	    System.out.println("Received selected items: " + selectedItems);
	    
	    // Add the amount and selected items to the model to use in the view
	    model.addAttribute("amount", amount);
	    model.addAttribute("selectedItems", selectedItems);
	    
	    // Redirect to a confirmation page or payment processing page
	    return "redirect:/paypal/indexx?amount=" + amount + "&selectedItems=" + selectedItems;
	}
	
    
    @PostMapping("/payment/create")
    public RedirectView createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description
    ) throws UnsupportedEncodingException {
    	
        try {
            String cancelUrl = "http://localhost:8081/payment/cancel";
            String successUrl = "http://localhost:8081/payment/success?description=" + URLEncoder.encode(description, "UTF-8");
            Payment payment = paypalService.createPayment(
                    Double.valueOf(amount),
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );
            
            for (Links links: payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
    		HttpServletRequest request,
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,@AuthenticationPrincipal CustomUserDetails currentUser
    ) {
    	
    	String description = request.getParameter("description");
    	System.out.print("description"+description);
    	List<Long> idList = Arrays.stream(description.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
            	User student = userservice.findById(currentUser.getUserId());
    	    	for (Long classId : idList) 
    	    	{
    	            ClassForSubject classForSubject = classforsubjectservice.findById(classId);
    	            Paymenttt paymentt = new Paymenttt();
    	            int classCredit = classForSubject.getSubject().getCredit();
    	            paymentt.setStudent(student);
    	            paymentt.setClassforSubject(classForSubject);
    	            int classPayment = classCredit * 3000000;
    	            paymentt.setCash(classPayment);
    	            paymentt.setPayments("Paypal");
    	            paymentt.setDate(new Date());
    	            paymentservice.save(paymentt);
    	        }
    	    	List<Long> listData=service.getListStudentRegistered(currentUser.getUserId(), idList);
    	    	service.updateItemsStatusToPayment(listData);
            	System.out.print("sucess");
            	
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        System.out.print("sucessss");
        return "paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }
}
