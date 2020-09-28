package com.loanplatform.service;

/**
 * Generic interface for notification
 */
public interface Notifcation<D> {
	Boolean send(D data);
}
