package com.epsm.epsmWeb.service;

import com.epsm.epsmCore.model.dispatch.Command;

public interface IncomingMessageService {
	void acceptCommand(Command command);
}
