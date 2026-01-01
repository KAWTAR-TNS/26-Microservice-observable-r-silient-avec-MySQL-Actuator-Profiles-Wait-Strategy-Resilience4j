package com.example.pricing_service.web;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/prices")
public class PricingController {

    @GetMapping("/{bookId}")
    public double price(@PathVariable long bookId,
                        @RequestParam(name = "fail", defaultValue = "false") boolean fail) {

        // 1) panne forcée pour tests
        if (fail) {
            throw new IllegalStateException("Pricing down (forced)");
        }

        // 2) panne aléatoire 30% pour simuler instabilité
        if (ThreadLocalRandom.current().nextInt(100) < 30) {
            throw new IllegalStateException("Random failure");
        }

        // 3) prix stable (exemple)
        return 50.0 + (bookId % 10) * 5.0;
    }
}