package com.auzmor.sample.sms.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.auzmor.sample.sms.validation.constraint.UserNumber;
import com.auzmor.sample.sms.validation.group.Inbound;
import com.auzmor.sample.sms.validation.group.Outbound;

public final class SmsMessage {

	@NotNull(message = "from is missing")
	@Size(min = 6, max = 16, message = "from is invalid")
	@Pattern(regexp = "^[0-9]*$", message = "from is not a number")
	@UserNumber(message = "from parameter not found", groups = Outbound.class)
	private String from;

	@NotNull(message = "to is missing")
	@Size(min = 6, max = 16, message = "to is invalid")
	@Pattern(regexp = "^[0-9]*$", message = "to is not a number")
	@UserNumber(message = "to parameter not found", groups = Inbound.class)
	private String to;

	@NotNull
	@Size(min = 1, max = 120)
	private String text;

	public SmsMessage(String from, String to, String text) {
		this.from = from;
		this.to = to;
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "SmsMessage [from=" + from + ", to=" + to + ", text=" + text + "]";
	}

}
