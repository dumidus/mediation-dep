/*
 *
 *  Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package com.wso2telco.dep.mediator.impl.provision;

import com.wso2telco.dep.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.wso2telco.dep.oneapivalidation.service.IServiceValidate;
import com.wso2telco.dep.oneapivalidation.service.impl.provision.ValidateRemoveService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;

public class RemoveServiceHandler implements ProvisionHandler {

	private Log log = LogFactory.getLog(RemoveServiceHandler.class);

	private static final String API_TYPE = "provision";
	private OriginatingCountryCalculatorIDD occi;
	private ProvisionExecutor executor;

	public RemoveServiceHandler(ProvisionExecutor executor) {

		this.executor = executor;
		occi = new OriginatingCountryCalculatorIDD();
	}

	@Override
	public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context)
			throws Exception {

		if (!httpMethod.equalsIgnoreCase("DELETE")) {

			log.debug("invalid http method in remove service validate : " + httpMethod);
			((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
			throw new Exception("Method not allowed");
		}

		String[] params = executor.getSubResourcePath().split("/");
		IServiceValidate validator = new ValidateRemoveService();

		validator.validateUrl(requestPath);
		validator.validate(params);
		validator.validate(jsonBody.toString());

		return true;
	}

	@Override
	public boolean handle(MessageContext context) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
