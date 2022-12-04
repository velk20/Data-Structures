package couponsopsjava;

import java.util.*;
import java.util.stream.Collectors;


public class CouponOperation implements ICouponOperation {

    private Map<String, Coupon> couponWithCodes;
    private Map<String, List<Coupon>> websiteWithCoupons;
    private Map<String, Website> websitesWithDomains;

    public CouponOperation() {
        this.couponWithCodes = new HashMap<>();
        this.websitesWithDomains = new HashMap<>();
        this.websiteWithCoupons = new HashMap<>();
    }

    public void registerSite(Website w) {
        if (this.websitesWithDomains.containsKey(w.domain)) {
            throw new IllegalArgumentException();
        }

        this.websiteWithCoupons.put(w.domain, new ArrayList<>());
        this.websitesWithDomains.put(w.domain, w);
    }

    public void addCoupon(Website w, Coupon c) {
        if (!exist(w)) {
            throw new IllegalArgumentException();
        }

        if (this.websiteWithCoupons.get(w.domain).contains(c)) {
            throw new IllegalArgumentException();
        }
        this.couponWithCodes.put(c.code, c);
        this.websiteWithCoupons.get(w.domain).add(c);

    }

    public Website removeWebsite(String domain) {
        if (!this.websitesWithDomains.containsKey(domain)) {
            throw new IllegalArgumentException();
        }
        Website removedWebsite = this.websitesWithDomains.remove(domain);
        List<Coupon> coupons = this.websiteWithCoupons.remove(removedWebsite.domain);
        for (Coupon coupon : coupons) {
            this.couponWithCodes.remove(coupon.code);
        }
        return removedWebsite;
    }

    public Coupon removeCoupon(String code) {
        if (!this.couponWithCodes.containsKey(code)) {
            throw new IllegalArgumentException();
        }

        return this.couponWithCodes.remove(code);
    }

    public boolean exist(Website w) {
        return this.websitesWithDomains.containsKey(w.domain);
    }

    public boolean exist(Coupon c) {
        return this.couponWithCodes.containsKey(c.code);
    }

    public Collection<Website> getSites() {
        return this.websitesWithDomains.values();
    }

    public Collection<Coupon> getCouponsForWebsite(Website w) {
        if (!exist(w)) {
            throw new IllegalArgumentException();
        }

        return this.websiteWithCoupons.get(w.domain);
    }

    public void useCoupon(Website w, Coupon c) {
        if (!exist(w) || !exist(c)) {
            throw new IllegalArgumentException();
        }

        if (!this.websiteWithCoupons.get(w.domain).contains(c)) {
            throw new IllegalArgumentException();
        }

        this.websiteWithCoupons.get(w.domain).remove(c);
        this.couponWithCodes.remove(c.code);
    }

    public Collection<Coupon> getCouponsOrderedByValidityDescAndDiscountPercentageDesc() {
        return this.couponWithCodes.values()
                .stream()
                .sorted((c1, c2) -> {
                    int c1Validity = c1.validity;
                    int c2Validity = c2.validity;
                    if (c1Validity == c2Validity) {
                        return Integer.compare(c2.discountPercentage, c1.discountPercentage);
                    }

                    return Integer.compare(c2Validity, c1Validity);
                }).collect(Collectors.toList());
    }

    public Collection<Website> getWebsitesOrderedByUserCountAndCouponsCountDesc() {
        return this.websitesWithDomains.values()
                .stream()
                .sorted((w1,w2)->{
                    int w1users = w1.usersCount;
                    int w2users = w2.usersCount;
                    if (w1users == w2users) {
                        int w1Size = this.websiteWithCoupons.get(w1.domain).size();
                        int w2Size = this.websiteWithCoupons.get(w2.domain).size();
                        return Integer.compare(w2Size, w1Size);
                    }

                    return Integer.compare(w1users, w2users);
                }).collect(Collectors.toList());
    }
}
