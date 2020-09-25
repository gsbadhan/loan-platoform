package com.loanplatform.pojo;

import java.util.Date;

import com.loanplatform.common.CarBodyType;
import com.loanplatform.common.FuelType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDetail {
	private String registrationNumber;
	private String registrationPlace;
	private String policyNumber;
	private String chassisNumber;
	private String engineNumber;
	private String model;
	private String cc;
	private String mobileNumber;
	private CarBodyType bodyType;
	private FuelType fuelType;
	private int sittingCapacity;
	private Date manufacturingDate;
	private Date saleDate;

}
