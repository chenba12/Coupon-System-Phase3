package com.project.CouponSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.CouponSystem.beans.Coupon;

public interface CouponRepo extends JpaRepository<Coupon,Long> {

	public Coupon findCouponById(long id);

	public Coupon findCouponByTitle(String title);


}
