package com.epsm.epsmWeb.service;

import com.epsm.epsmWeb.model.dispatch.Command;

public interface IncomingMessageService {
	void acceptCommand(Command command);
}
