 
package com.haier.webservices.client.hps.project;


 
public class ProjectWebServiceServerStub extends org.apache.axis2.client.Stub {
    private static int counter = 0;
    protected org.apache.axis2.description.AxisOperation[] _operations;

    //hashmaps to keep the fault mapping
    private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
    private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
    private java.util.HashMap faultMessageMap = new java.util.HashMap();
    private javax.xml.namespace.QName[] opNameArray = null;

     
    public ProjectWebServiceServerStub(
        org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(configurationContext, targetEndpoint, false);
    }

     
    public ProjectWebServiceServerStub(
        org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
        //To populate AxisService
        populateAxisService();
        populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,
                _service);

        _serviceClient.getOptions()
                      .setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
    }

     
    public ProjectWebServiceServerStub(
        org.apache.axis2.context.ConfigurationContext configurationContext)
        throws org.apache.axis2.AxisFault {
        this(configurationContext, "http://10.138.10.68:8090/soap/project");
    }

     
    public ProjectWebServiceServerStub() throws org.apache.axis2.AxisFault {
        this("http://10.138.10.68:8090/soap/project");
    }

     
    public ProjectWebServiceServerStub(java.lang.String targetEndpoint)
        throws org.apache.axis2.AxisFault {
        this(null, targetEndpoint);
    }

    private static synchronized java.lang.String getUniqueSuffix() {
        // reset the counter if it is greater than 99999
        if (counter > 99999) {
            counter = 0;
        }

        counter = counter + 1;

        return java.lang.Long.toString(java.lang.System.currentTimeMillis()) +
        "_" + counter;
    }

    private void populateAxisService() throws org.apache.axis2.AxisFault {
        //creating the Service with a unique name
        _service = new org.apache.axis2.description.AxisService(
                "ProjectWebServiceServer" + getUniqueSuffix());
        addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[2];

        __operation = new org.apache.axis2.description.OutInAxisOperation();

        __operation.setName(new javax.xml.namespace.QName(
                "http://project.funnel.api.manage.hps.com/",
                "saveProjectFromQYG"));
        _service.addOperation(__operation);

        _operations[0] = __operation;

        __operation = new org.apache.axis2.description.OutInAxisOperation();

        __operation.setName(new javax.xml.namespace.QName(
                "http://project.funnel.api.manage.hps.com/",
                "queryProjectManagerFromHPS"));
        _service.addOperation(__operation);

        _operations[1] = __operation;
    }

    //populates the faults
    private void populateFaults() {
    }

     
    public ProjectWebServiceServerStub.SaveProjectFromQYGResponseE saveProjectFromQYG(
        ProjectWebServiceServerStub.SaveProjectFromQYGE saveProjectFromQYG0)
        throws java.rmi.RemoteException {
        org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

        try {
            org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
            _operationClient.getOptions()
                            .setAction("http://project.funnel.api.manage.hps.com/ProjectWebServiceServer/saveProjectFromQYG");
            _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

            addPropertyToOperationClient(_operationClient,
                org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
                "&");

            // create SOAP envelope with that payload
            org.apache.axiom.soap.SOAPEnvelope env = null;

            env = toEnvelope(getFactory(_operationClient.getOptions()
                                                        .getSoapVersionURI()),
                    saveProjectFromQYG0,
                    optimizeContent(
                        new javax.xml.namespace.QName(
                            "http://project.funnel.api.manage.hps.com/",
                            "saveProjectFromQYG")),
                    new javax.xml.namespace.QName(
                        "http://project.funnel.api.manage.hps.com/",
                        "saveProjectFromQYG"));

            //adding SOAP soap_headers
            _serviceClient.addHeadersToEnvelope(env);
            // set the message context with that soap envelope
            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

            //execute the operation client
            _operationClient.execute(true);

            org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
            org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

            java.lang.Object object = fromOM(_returnEnv.getBody()
                                                       .getFirstElement(),
                    ProjectWebServiceServerStub.SaveProjectFromQYGResponseE.class);

            return (ProjectWebServiceServerStub.SaveProjectFromQYGResponseE) object;
        } catch (org.apache.axis2.AxisFault f) {
            org.apache.axiom.om.OMElement faultElt = f.getDetail();

            if (faultElt != null) {
                if (faultExceptionNameMap.containsKey(
                            new org.apache.axis2.client.FaultMapKey(
                                faultElt.getQName(), "saveProjectFromQYG"))) {
                    //make the fault by reflection
                    try {
                        java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(
                                    faultElt.getQName(), "saveProjectFromQYG"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(java.lang.String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());

                        //message class
                        java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                                    faultElt.getQName(), "saveProjectFromQYG"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,
                                messageClass);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                new java.lang.Class[] { messageClass });
                        m.invoke(ex, new java.lang.Object[] { messageObject });

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    } catch (java.lang.ClassCastException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                } else {
                    throw f;
                }
            } else {
                throw f;
            }
        } finally {
            if (_messageContext.getTransportOut() != null) {
                _messageContext.getTransportOut().getSender()
                               .cleanup(_messageContext);
            }
        }
    }

     
    public void startsaveProjectFromQYG(
        ProjectWebServiceServerStub.SaveProjectFromQYGE saveProjectFromQYG0,
        final ProjectWebServiceServerCallbackHandler callback)
        throws java.rmi.RemoteException {
        org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
        _operationClient.getOptions()
                        .setAction("http://project.funnel.api.manage.hps.com/ProjectWebServiceServer/saveProjectFromQYG");
        _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

        addPropertyToOperationClient(_operationClient,
            org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
            "&");

        // create SOAP envelope with that payload
        org.apache.axiom.soap.SOAPEnvelope env = null;
        final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

        //Style is Doc.
        env = toEnvelope(getFactory(_operationClient.getOptions()
                                                    .getSoapVersionURI()),
                saveProjectFromQYG0,
                optimizeContent(
                    new javax.xml.namespace.QName(
                        "http://project.funnel.api.manage.hps.com/",
                        "saveProjectFromQYG")),
                new javax.xml.namespace.QName(
                    "http://project.funnel.api.manage.hps.com/",
                    "saveProjectFromQYG"));

        // adding SOAP soap_headers
        _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);

        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                public void onMessage(
                    org.apache.axis2.context.MessageContext resultContext) {
                    try {
                        org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

                        java.lang.Object object = fromOM(resultEnv.getBody()
                                                                  .getFirstElement(),
                                ProjectWebServiceServerStub.SaveProjectFromQYGResponseE.class);
                        callback.receiveResultsaveProjectFromQYG((ProjectWebServiceServerStub.SaveProjectFromQYGResponseE) object);
                    } catch (org.apache.axis2.AxisFault e) {
                        callback.receiveErrorsaveProjectFromQYG(e);
                    }
                }

                public void onError(java.lang.Exception error) {
                    if (error instanceof org.apache.axis2.AxisFault) {
                        org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
                        org.apache.axiom.om.OMElement faultElt = f.getDetail();

                        if (faultElt != null) {
                            if (faultExceptionNameMap.containsKey(
                                        new org.apache.axis2.client.FaultMapKey(
                                            faultElt.getQName(),
                                            "saveProjectFromQYG"))) {
                                //make the fault by reflection
                                try {
                                    java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(
                                                faultElt.getQName(),
                                                "saveProjectFromQYG"));
                                    java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                                    java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(java.lang.String.class);
                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());

                                    //message class
                                    java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                                                faultElt.getQName(),
                                                "saveProjectFromQYG"));
                                    java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                                    java.lang.Object messageObject = fromOM(faultElt,
                                            messageClass);
                                    java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                            new java.lang.Class[] { messageClass });
                                    m.invoke(ex,
                                        new java.lang.Object[] { messageObject });

                                    callback.receiveErrorsaveProjectFromQYG(new java.rmi.RemoteException(
                                            ex.getMessage(), ex));
                                } catch (java.lang.ClassCastException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorsaveProjectFromQYG(f);
                                } catch (java.lang.ClassNotFoundException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorsaveProjectFromQYG(f);
                                } catch (java.lang.NoSuchMethodException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorsaveProjectFromQYG(f);
                                } catch (java.lang.reflect.InvocationTargetException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorsaveProjectFromQYG(f);
                                } catch (java.lang.IllegalAccessException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorsaveProjectFromQYG(f);
                                } catch (java.lang.InstantiationException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorsaveProjectFromQYG(f);
                                } catch (org.apache.axis2.AxisFault e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorsaveProjectFromQYG(f);
                                }
                            } else {
                                callback.receiveErrorsaveProjectFromQYG(f);
                            }
                        } else {
                            callback.receiveErrorsaveProjectFromQYG(f);
                        }
                    } else {
                        callback.receiveErrorsaveProjectFromQYG(error);
                    }
                }

                public void onFault(
                    org.apache.axis2.context.MessageContext faultContext) {
                    org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                    onError(fault);
                }

                public void onComplete() {
                    try {
                        _messageContext.getTransportOut().getSender()
                                       .cleanup(_messageContext);
                    } catch (org.apache.axis2.AxisFault axisFault) {
                        callback.receiveErrorsaveProjectFromQYG(axisFault);
                    }
                }
            });

        org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;

        if ((_operations[0].getMessageReceiver() == null) &&
                _operationClient.getOptions().isUseSeparateListener()) {
            _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
            _operations[0].setMessageReceiver(_callbackReceiver);
        }

        //execute the operation client
        _operationClient.execute(false);
    }

     
    public ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE queryProjectManagerFromHPS(
        ProjectWebServiceServerStub.QueryProjectManagerFromHPSE queryProjectManagerFromHPS2)
        throws java.rmi.RemoteException {
        org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

        try {
            org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
            _operationClient.getOptions()
                            .setAction("http://project.funnel.api.manage.hps.com/ProjectWebServiceServer/queryProjectManagerFromHPS");
            _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

            addPropertyToOperationClient(_operationClient,
                org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
                "&");

            // create SOAP envelope with that payload
            org.apache.axiom.soap.SOAPEnvelope env = null;

            env = toEnvelope(getFactory(_operationClient.getOptions()
                                                        .getSoapVersionURI()),
                    queryProjectManagerFromHPS2,
                    optimizeContent(
                        new javax.xml.namespace.QName(
                            "http://project.funnel.api.manage.hps.com/",
                            "queryProjectManagerFromHPS")),
                    new javax.xml.namespace.QName(
                        "http://project.funnel.api.manage.hps.com/",
                        "queryProjectManagerFromHPS"));

            //adding SOAP soap_headers
            _serviceClient.addHeadersToEnvelope(env);
            // set the message context with that soap envelope
            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

            //execute the operation client
            _operationClient.execute(true);

            org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
            org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

            java.lang.Object object = fromOM(_returnEnv.getBody()
                                                       .getFirstElement(),
                    ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE.class);

            return (ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE) object;
        } catch (org.apache.axis2.AxisFault f) {
            org.apache.axiom.om.OMElement faultElt = f.getDetail();

            if (faultElt != null) {
                if (faultExceptionNameMap.containsKey(
                            new org.apache.axis2.client.FaultMapKey(
                                faultElt.getQName(),
                                "queryProjectManagerFromHPS"))) {
                    //make the fault by reflection
                    try {
                        java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(
                                    faultElt.getQName(),
                                    "queryProjectManagerFromHPS"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(java.lang.String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());

                        //message class
                        java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                                    faultElt.getQName(),
                                    "queryProjectManagerFromHPS"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,
                                messageClass);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                new java.lang.Class[] { messageClass });
                        m.invoke(ex, new java.lang.Object[] { messageObject });

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    } catch (java.lang.ClassCastException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                } else {
                    throw f;
                }
            } else {
                throw f;
            }
        } finally {
            if (_messageContext.getTransportOut() != null) {
                _messageContext.getTransportOut().getSender()
                               .cleanup(_messageContext);
            }
        }
    }

     
    public void startqueryProjectManagerFromHPS(
        ProjectWebServiceServerStub.QueryProjectManagerFromHPSE queryProjectManagerFromHPS2,
        final ProjectWebServiceServerCallbackHandler callback)
        throws java.rmi.RemoteException {
        org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
        _operationClient.getOptions()
                        .setAction("http://project.funnel.api.manage.hps.com/ProjectWebServiceServer/queryProjectManagerFromHPS");
        _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

        addPropertyToOperationClient(_operationClient,
            org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
            "&");

        // create SOAP envelope with that payload
        org.apache.axiom.soap.SOAPEnvelope env = null;
        final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

        //Style is Doc.
        env = toEnvelope(getFactory(_operationClient.getOptions()
                                                    .getSoapVersionURI()),
                queryProjectManagerFromHPS2,
                optimizeContent(
                    new javax.xml.namespace.QName(
                        "http://project.funnel.api.manage.hps.com/",
                        "queryProjectManagerFromHPS")),
                new javax.xml.namespace.QName(
                    "http://project.funnel.api.manage.hps.com/",
                    "queryProjectManagerFromHPS"));

        // adding SOAP soap_headers
        _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);

        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                public void onMessage(
                    org.apache.axis2.context.MessageContext resultContext) {
                    try {
                        org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

                        java.lang.Object object = fromOM(resultEnv.getBody()
                                                                  .getFirstElement(),
                                ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE.class);
                        callback.receiveResultqueryProjectManagerFromHPS((ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE) object);
                    } catch (org.apache.axis2.AxisFault e) {
                        callback.receiveErrorqueryProjectManagerFromHPS(e);
                    }
                }

                public void onError(java.lang.Exception error) {
                    if (error instanceof org.apache.axis2.AxisFault) {
                        org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
                        org.apache.axiom.om.OMElement faultElt = f.getDetail();

                        if (faultElt != null) {
                            if (faultExceptionNameMap.containsKey(
                                        new org.apache.axis2.client.FaultMapKey(
                                            faultElt.getQName(),
                                            "queryProjectManagerFromHPS"))) {
                                //make the fault by reflection
                                try {
                                    java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(
                                                faultElt.getQName(),
                                                "queryProjectManagerFromHPS"));
                                    java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                                    java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(java.lang.String.class);
                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());

                                    //message class
                                    java.lang.String messageClassName = (java.lang.String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                                                faultElt.getQName(),
                                                "queryProjectManagerFromHPS"));
                                    java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                                    java.lang.Object messageObject = fromOM(faultElt,
                                            messageClass);
                                    java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                            new java.lang.Class[] { messageClass });
                                    m.invoke(ex,
                                        new java.lang.Object[] { messageObject });

                                    callback.receiveErrorqueryProjectManagerFromHPS(new java.rmi.RemoteException(
                                            ex.getMessage(), ex));
                                } catch (java.lang.ClassCastException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorqueryProjectManagerFromHPS(f);
                                } catch (java.lang.ClassNotFoundException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorqueryProjectManagerFromHPS(f);
                                } catch (java.lang.NoSuchMethodException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorqueryProjectManagerFromHPS(f);
                                } catch (java.lang.reflect.InvocationTargetException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorqueryProjectManagerFromHPS(f);
                                } catch (java.lang.IllegalAccessException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorqueryProjectManagerFromHPS(f);
                                } catch (java.lang.InstantiationException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorqueryProjectManagerFromHPS(f);
                                } catch (org.apache.axis2.AxisFault e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorqueryProjectManagerFromHPS(f);
                                }
                            } else {
                                callback.receiveErrorqueryProjectManagerFromHPS(f);
                            }
                        } else {
                            callback.receiveErrorqueryProjectManagerFromHPS(f);
                        }
                    } else {
                        callback.receiveErrorqueryProjectManagerFromHPS(error);
                    }
                }

                public void onFault(
                    org.apache.axis2.context.MessageContext faultContext) {
                    org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                    onError(fault);
                }

                public void onComplete() {
                    try {
                        _messageContext.getTransportOut().getSender()
                                       .cleanup(_messageContext);
                    } catch (org.apache.axis2.AxisFault axisFault) {
                        callback.receiveErrorqueryProjectManagerFromHPS(axisFault);
                    }
                }
            });

        org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;

        if ((_operations[1].getMessageReceiver() == null) &&
                _operationClient.getOptions().isUseSeparateListener()) {
            _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
            _operations[1].setMessageReceiver(_callbackReceiver);
        }

        //execute the operation client
        _operationClient.execute(false);
    }

    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        if (opNameArray == null) {
            return false;
        }

        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;
            }
        }

        return false;
    }

    private org.apache.axiom.om.OMElement toOM(
        ProjectWebServiceServerStub.SaveProjectFromQYGE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(ProjectWebServiceServerStub.SaveProjectFromQYGE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        ProjectWebServiceServerStub.SaveProjectFromQYGResponseE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(ProjectWebServiceServerStub.SaveProjectFromQYGResponseE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        ProjectWebServiceServerStub.QueryProjectManagerFromHPSE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(ProjectWebServiceServerStub.QueryProjectManagerFromHPSE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        ProjectWebServiceServerStub.SaveProjectFromQYGE param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    ProjectWebServiceServerStub.SaveProjectFromQYGE.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

     
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        ProjectWebServiceServerStub.QueryProjectManagerFromHPSE param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    ProjectWebServiceServerStub.QueryProjectManagerFromHPSE.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

     

     
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        java.lang.Class type) throws org.apache.axis2.AxisFault {
        try {
            if (ProjectWebServiceServerStub.QueryProjectManagerFromHPSE.class.equals(
                        type)) {
                return ProjectWebServiceServerStub.QueryProjectManagerFromHPSE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE.class.equals(
                        type)) {
                return ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (ProjectWebServiceServerStub.SaveProjectFromQYGE.class.equals(
                        type)) {
                return ProjectWebServiceServerStub.SaveProjectFromQYGE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (ProjectWebServiceServerStub.SaveProjectFromQYGResponseE.class.equals(
                        type)) {
                return ProjectWebServiceServerStub.SaveProjectFromQYGResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    //http://10.138.10.68:8090/soap/project
    public static class SaveProjectFromQYGResponseE implements org.apache.axis2.databinding.ADBBean {
        public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://project.funnel.api.manage.hps.com/",
                "saveProjectFromQYGResponse", "ns1");

         
        protected SaveProjectFromQYGResponse localSaveProjectFromQYGResponse;

         
        public SaveProjectFromQYGResponse getSaveProjectFromQYGResponse() {
            return localSaveProjectFromQYGResponse;
        }

         
        public void setSaveProjectFromQYGResponse(
            SaveProjectFromQYGResponse param) {
            this.localSaveProjectFromQYGResponse = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, MY_QNAME));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            //We can safely assume an element has only one type associated with it
            if (localSaveProjectFromQYGResponse == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "saveProjectFromQYGResponse cannot be null!");
            }

            localSaveProjectFromQYGResponse.serialize(MY_QNAME, xmlWriter);
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static SaveProjectFromQYGResponseE parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                SaveProjectFromQYGResponseE object = new SaveProjectFromQYGResponseE();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    while (!reader.isEndElement()) {
                        if (reader.isStartElement()) {
                            if (reader.isStartElement() &&
                                    new javax.xml.namespace.QName(
                                        "http://project.funnel.api.manage.hps.com/",
                                        "saveProjectFromQYGResponse").equals(
                                        reader.getName())) {
                                object.setSaveProjectFromQYGResponse(SaveProjectFromQYGResponse.Factory.parse(
                                        reader));
                            } // End of if for expected property start element

                            else {
                                // 3 - A start element we are not expecting indicates an invalid parameter was passed
                                throw new org.apache.axis2.databinding.ADBException(
                                    "Unexpected subelement " +
                                    reader.getName());
                            }
                        } else {
                            reader.next();
                        }
                    } // end of while loop
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class ProjectRTwoSaveParam extends ToString implements org.apache.axis2.databinding.ADBBean {
         

         
        protected boolean localBeSplitShipment;

         
        protected boolean localBeSplitShipmentTracker = false;

         
        protected boolean localBeTemplate;

         
        protected boolean localBeTemplateTracker = false;

         
        protected java.lang.String localChancePoint;

         
        protected boolean localChancePointTracker = false;

         
        protected java.lang.String localCreatedById;

         
        protected boolean localCreatedByIdTracker = false;

         
        protected java.math.BigDecimal localEstimatedDeliveryAmount;

         
        protected boolean localEstimatedDeliveryAmountTracker = false;

         
        protected java.math.BigDecimal localEstimatedInstallationAmount;

         
        protected boolean localEstimatedInstallationAmountTracker = false;

         
        protected java.lang.String localFirstContactName;

         
        protected boolean localFirstContactNameTracker = false;

         
        protected java.lang.String localId;

         
        protected boolean localIdTracker = false;

         
        protected java.lang.String localIndustryCategory;

         
        protected boolean localIndustryCategoryTracker = false;

         
        protected java.lang.String localIndustryHomeCategory;

         
        protected boolean localIndustryHomeCategoryTracker = false;

         
        protected java.lang.String localLastModifiedById;

         
        protected boolean localLastModifiedByIdTracker = false;

         
        protected java.lang.String localLockUser;

         
        protected boolean localLockUserTracker = false;

         
        protected java.lang.String localPhone;

         
        protected boolean localPhoneTracker = false;

         
        protected java.lang.String localPosition;

         
        protected boolean localPositionTracker = false;

         
        protected java.lang.String localProjectDevelopPlan;

         
        protected boolean localProjectDevelopPlanTracker = false;

         
        protected java.lang.String localProjectFinancialStatus;

         
        protected boolean localProjectFinancialStatusTracker = false;

         
        protected java.lang.String localProjectId;

         
        protected boolean localProjectIdTracker = false;

         
        protected java.lang.String localProjectSpecificLocation;

         
        protected boolean localProjectSpecificLocationTracker = false;

         
        protected java.lang.String localProjectValueProposition;

         
        protected boolean localProjectValuePropositionTracker = false;

         
        protected java.lang.String localUserGroup;

         
        protected boolean localUserGroupTracker = false;

        public boolean isBeSplitShipmentSpecified() {
            return localBeSplitShipmentTracker;
        }

         
        public boolean getBeSplitShipment() {
            return localBeSplitShipment;
        }

         
        public void setBeSplitShipment(boolean param) {
            // setting primitive attribute tracker to true
            localBeSplitShipmentTracker = true;

            this.localBeSplitShipment = param;
        }

        public boolean isBeTemplateSpecified() {
            return localBeTemplateTracker;
        }

         
        public boolean getBeTemplate() {
            return localBeTemplate;
        }

         
        public void setBeTemplate(boolean param) {
            // setting primitive attribute tracker to true
            localBeTemplateTracker = true;

            this.localBeTemplate = param;
        }

        public boolean isChancePointSpecified() {
            return localChancePointTracker;
        }

         
        public java.lang.String getChancePoint() {
            return localChancePoint;
        }

         
        public void setChancePoint(java.lang.String param) {
            localChancePointTracker = param != null;

            this.localChancePoint = param;
        }

        public boolean isCreatedByIdSpecified() {
            return localCreatedByIdTracker;
        }

         
        public java.lang.String getCreatedById() {
            return localCreatedById;
        }

         
        public void setCreatedById(java.lang.String param) {
            localCreatedByIdTracker = param != null;

            this.localCreatedById = param;
        }

        public boolean isEstimatedDeliveryAmountSpecified() {
            return localEstimatedDeliveryAmountTracker;
        }

         
        public java.math.BigDecimal getEstimatedDeliveryAmount() {
            return localEstimatedDeliveryAmount;
        }

         
        public void setEstimatedDeliveryAmount(java.math.BigDecimal param) {
            localEstimatedDeliveryAmountTracker = param != null;

            this.localEstimatedDeliveryAmount = param;
        }

        public boolean isEstimatedInstallationAmountSpecified() {
            return localEstimatedInstallationAmountTracker;
        }

         
        public java.math.BigDecimal getEstimatedInstallationAmount() {
            return localEstimatedInstallationAmount;
        }

         
        public void setEstimatedInstallationAmount(java.math.BigDecimal param) {
            localEstimatedInstallationAmountTracker = param != null;

            this.localEstimatedInstallationAmount = param;
        }

        public boolean isFirstContactNameSpecified() {
            return localFirstContactNameTracker;
        }

         
        public java.lang.String getFirstContactName() {
            return localFirstContactName;
        }

         
        public void setFirstContactName(java.lang.String param) {
            localFirstContactNameTracker = param != null;

            this.localFirstContactName = param;
        }

        public boolean isIdSpecified() {
            return localIdTracker;
        }

         
        public java.lang.String getId() {
            return localId;
        }

         
        public void setId(java.lang.String param) {
            localIdTracker = param != null;

            this.localId = param;
        }

        public boolean isIndustryCategorySpecified() {
            return localIndustryCategoryTracker;
        }

         
        public java.lang.String getIndustryCategory() {
            return localIndustryCategory;
        }

         
        public void setIndustryCategory(java.lang.String param) {
            localIndustryCategoryTracker = param != null;

            this.localIndustryCategory = param;
        }

        public boolean isIndustryHomeCategorySpecified() {
            return localIndustryHomeCategoryTracker;
        }

         
        public java.lang.String getIndustryHomeCategory() {
            return localIndustryHomeCategory;
        }

         
        public void setIndustryHomeCategory(java.lang.String param) {
            localIndustryHomeCategoryTracker = param != null;

            this.localIndustryHomeCategory = param;
        }

        public boolean isLastModifiedByIdSpecified() {
            return localLastModifiedByIdTracker;
        }

         
        public java.lang.String getLastModifiedById() {
            return localLastModifiedById;
        }

         
        public void setLastModifiedById(java.lang.String param) {
            localLastModifiedByIdTracker = param != null;

            this.localLastModifiedById = param;
        }

        public boolean isLockUserSpecified() {
            return localLockUserTracker;
        }

         
        public java.lang.String getLockUser() {
            return localLockUser;
        }

         
        public void setLockUser(java.lang.String param) {
            localLockUserTracker = param != null;

            this.localLockUser = param;
        }

        public boolean isPhoneSpecified() {
            return localPhoneTracker;
        }

         
        public java.lang.String getPhone() {
            return localPhone;
        }

         
        public void setPhone(java.lang.String param) {
            localPhoneTracker = param != null;

            this.localPhone = param;
        }

        public boolean isPositionSpecified() {
            return localPositionTracker;
        }

         
        public java.lang.String getPosition() {
            return localPosition;
        }

         
        public void setPosition(java.lang.String param) {
            localPositionTracker = param != null;

            this.localPosition = param;
        }

        public boolean isProjectDevelopPlanSpecified() {
            return localProjectDevelopPlanTracker;
        }

         
        public java.lang.String getProjectDevelopPlan() {
            return localProjectDevelopPlan;
        }

         
        public void setProjectDevelopPlan(java.lang.String param) {
            localProjectDevelopPlanTracker = param != null;

            this.localProjectDevelopPlan = param;
        }

        public boolean isProjectFinancialStatusSpecified() {
            return localProjectFinancialStatusTracker;
        }

         
        public java.lang.String getProjectFinancialStatus() {
            return localProjectFinancialStatus;
        }

         
        public void setProjectFinancialStatus(java.lang.String param) {
            localProjectFinancialStatusTracker = param != null;

            this.localProjectFinancialStatus = param;
        }

        public boolean isProjectIdSpecified() {
            return localProjectIdTracker;
        }

         
        public java.lang.String getProjectId() {
            return localProjectId;
        }

         
        public void setProjectId(java.lang.String param) {
            localProjectIdTracker = param != null;

            this.localProjectId = param;
        }

        public boolean isProjectSpecificLocationSpecified() {
            return localProjectSpecificLocationTracker;
        }

         
        public java.lang.String getProjectSpecificLocation() {
            return localProjectSpecificLocation;
        }

         
        public void setProjectSpecificLocation(java.lang.String param) {
            localProjectSpecificLocationTracker = param != null;

            this.localProjectSpecificLocation = param;
        }

        public boolean isProjectValuePropositionSpecified() {
            return localProjectValuePropositionTracker;
        }

         
        public java.lang.String getProjectValueProposition() {
            return localProjectValueProposition;
        }

         
        public void setProjectValueProposition(java.lang.String param) {
            localProjectValuePropositionTracker = param != null;

            this.localProjectValueProposition = param;
        }

        public boolean isUserGroupSpecified() {
            return localUserGroupTracker;
        }

         
        public java.lang.String getUserGroup() {
            return localUserGroup;
        }

         
        public void setUserGroup(java.lang.String param) {
            localUserGroupTracker = param != null;

            this.localUserGroup = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://project.funnel.api.manage.hps.com/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":projectRTwoSaveParam", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "projectRTwoSaveParam", xmlWriter);
            }

            if (localBeSplitShipmentTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beSplitShipment", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beSplitShipment cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeSplitShipment));
                }

                xmlWriter.writeEndElement();
            }

            if (localBeTemplateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beTemplate", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beTemplate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeTemplate));
                }

                xmlWriter.writeEndElement();
            }

            if (localChancePointTracker) {
                namespace = "";
                writeStartElement(null, namespace, "chancePoint", xmlWriter);

                if (localChancePoint == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "chancePoint cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localChancePoint);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdById", xmlWriter);

                if (localCreatedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localEstimatedDeliveryAmountTracker) {
                namespace = "";
                writeStartElement(null, namespace, "estimatedDeliveryAmount",
                    xmlWriter);

                if (localEstimatedDeliveryAmount == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "estimatedDeliveryAmount cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localEstimatedDeliveryAmount));
                }

                xmlWriter.writeEndElement();
            }

            if (localEstimatedInstallationAmountTracker) {
                namespace = "";
                writeStartElement(null, namespace,
                    "estimatedInstallationAmount", xmlWriter);

                if (localEstimatedInstallationAmount == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "estimatedInstallationAmount cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localEstimatedInstallationAmount));
                }

                xmlWriter.writeEndElement();
            }

            if (localFirstContactNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "firstContactName", xmlWriter);

                if (localFirstContactName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "firstContactName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localFirstContactName);
                }

                xmlWriter.writeEndElement();
            }

            if (localIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "id", xmlWriter);

                if (localId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "id cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localId);
                }

                xmlWriter.writeEndElement();
            }

            if (localIndustryCategoryTracker) {
                namespace = "";
                writeStartElement(null, namespace, "industryCategory", xmlWriter);

                if (localIndustryCategory == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "industryCategory cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localIndustryCategory);
                }

                xmlWriter.writeEndElement();
            }

            if (localIndustryHomeCategoryTracker) {
                namespace = "";
                writeStartElement(null, namespace, "industryHomeCategory",
                    xmlWriter);

                if (localIndustryHomeCategory == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "industryHomeCategory cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localIndustryHomeCategory);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedById", xmlWriter);

                if (localLastModifiedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localLockUserTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lockUser", xmlWriter);

                if (localLockUser == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lockUser cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLockUser);
                }

                xmlWriter.writeEndElement();
            }

            if (localPhoneTracker) {
                namespace = "";
                writeStartElement(null, namespace, "phone", xmlWriter);

                if (localPhone == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "phone cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localPhone);
                }

                xmlWriter.writeEndElement();
            }

            if (localPositionTracker) {
                namespace = "";
                writeStartElement(null, namespace, "position", xmlWriter);

                if (localPosition == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "position cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localPosition);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectDevelopPlanTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectDevelopPlan",
                    xmlWriter);

                if (localProjectDevelopPlan == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectDevelopPlan cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectDevelopPlan);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectFinancialStatusTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectFinancialStatus",
                    xmlWriter);

                if (localProjectFinancialStatus == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectFinancialStatus cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectFinancialStatus);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectId", xmlWriter);

                if (localProjectId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectId);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectSpecificLocationTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectSpecificLocation",
                    xmlWriter);

                if (localProjectSpecificLocation == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectSpecificLocation cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectSpecificLocation);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectValuePropositionTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectValueProposition",
                    xmlWriter);

                if (localProjectValueProposition == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectValueProposition cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectValueProposition);
                }

                xmlWriter.writeEndElement();
            }

            if (localUserGroupTracker) {
                namespace = "";
                writeStartElement(null, namespace, "userGroup", xmlWriter);

                if (localUserGroup == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "userGroup cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localUserGroup);
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static ProjectRTwoSaveParam parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                ProjectRTwoSaveParam object = new ProjectRTwoSaveParam();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"projectRTwoSaveParam".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (ProjectRTwoSaveParam) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beSplitShipment").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beSplitShipment" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeSplitShipment(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beTemplate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beTemplate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeTemplate(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "chancePoint").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "chancePoint" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setChancePoint(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "estimatedDeliveryAmount").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "estimatedDeliveryAmount" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setEstimatedDeliveryAmount(org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "estimatedInstallationAmount").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " +
                                "estimatedInstallationAmount" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setEstimatedInstallationAmount(org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "firstContactName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "firstContactName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFirstContactName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "id").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "id" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "industryCategory").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "industryCategory" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setIndustryCategory(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "industryHomeCategory").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "industryHomeCategory" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setIndustryHomeCategory(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lockUser").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lockUser" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLockUser(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "phone").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "phone" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setPhone(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "position").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "position" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setPosition(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectDevelopPlan").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectDevelopPlan" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectDevelopPlan(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectFinancialStatus").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectFinancialStatus" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectFinancialStatus(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectSpecificLocation").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectSpecificLocation" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectSpecificLocation(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectValueProposition").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectValueProposition" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectValueProposition(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "userGroup").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "userGroup" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setUserGroup(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class ExtensionMapper {
        public static java.lang.Object getTypeObject(
            java.lang.String namespaceURI, java.lang.String typeName,
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "basicDto".equals(typeName)) {
                return BasicDto.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "projectRTwoSaveParam".equals(typeName)) {
                return ProjectRTwoSaveParam.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "projectRThreeSaveParam".equals(typeName)) {
                return ProjectRThreeSaveParam.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "toString".equals(typeName)) {
                return ToString.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "queryProjectManagerFromHPS".equals(typeName)) {
                return QueryProjectManagerFromHPS.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "projectPurchaseForecastSaveParam".equals(typeName)) {
                return ProjectPurchaseForecastSaveParam.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "projectSaveParam".equals(typeName)) {
                return ProjectSaveParam.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "projectROneSaveParam".equals(typeName)) {
                return ProjectROneSaveParam.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "saveProjectFromQYG".equals(typeName)) {
                return SaveProjectFromQYG.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "userDTO".equals(typeName)) {
                return UserDTO.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "saveProjectFromQYGResponse".equals(typeName)) {
                return SaveProjectFromQYGResponse.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "basePostTagContentDTO".equals(typeName)) {
                return BasePostTagContentDTO.Factory.parse(reader);
            }

            if ("http://project.funnel.api.manage.hps.com/".equals(namespaceURI) &&
                    "queryProjectManagerFromHPSResponse".equals(typeName)) {
                return QueryProjectManagerFromHPSResponse.Factory.parse(reader);
            }

            throw new org.apache.axis2.databinding.ADBException(
                "Unsupported type " + namespaceURI + " " + typeName);
        }
    }

    public static class QueryProjectManagerFromHPSResponse implements org.apache.axis2.databinding.ADBBean {
         

         
        protected UserDTO[] local_return;

         
        protected boolean local_returnTracker = false;

        public boolean is_returnSpecified() {
            return local_returnTracker;
        }

         
        public UserDTO[] get_return() {
            return local_return;
        }

         
        protected void validate_return(UserDTO[] param) {
        }

         
        public void set_return(UserDTO[] param) {
            validate_return(param);

            local_returnTracker = param != null;

            this.local_return = param;
        }

         
        public void add_return(UserDTO param) {
            if (local_return == null) {
                local_return = new UserDTO[] {  };
            }

            //update the setting tracker
            local_returnTracker = true;

            java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(local_return);
            list.add(param);
            this.local_return = (UserDTO[]) list.toArray(new UserDTO[list.size()]);
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            if (serializeType) {
                java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                        "http://project.funnel.api.manage.hps.com/");

                if ((namespacePrefix != null) &&
                        (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        namespacePrefix +
                        ":queryProjectManagerFromHPSResponse", xmlWriter);
                } else {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "queryProjectManagerFromHPSResponse", xmlWriter);
                }
            }

            if (local_returnTracker) {
                if (local_return != null) {
                    for (int i = 0; i < local_return.length; i++) {
                        if (local_return[i] != null) {
                            local_return[i].serialize(new javax.xml.namespace.QName(
                                    "", "return"), xmlWriter);
                        } else {
                            // we don't have to do any thing since minOccures is zero
                        }
                    }
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "return cannot be null!!");
                }
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static QueryProjectManagerFromHPSResponse parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                QueryProjectManagerFromHPSResponse object = new QueryProjectManagerFromHPSResponse();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"queryProjectManagerFromHPSResponse".equals(
                                        type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (QueryProjectManagerFromHPSResponse) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    java.util.ArrayList list1 = new java.util.ArrayList();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "return").equals(
                                reader.getName())) {
                        // Process the array and step past its final element's end.
                        list1.add(UserDTO.Factory.parse(reader));

                        //loop until we find a start element that is not part of this array
                        boolean loopDone1 = false;

                        while (!loopDone1) {
                            // We should be at the end element, but make sure
                            while (!reader.isEndElement())
                                reader.next();

                            // Step out of this element
                            reader.next();

                            // Step to next element event.
                            while (!reader.isStartElement() &&
                                    !reader.isEndElement())
                                reader.next();

                            if (reader.isEndElement()) {
                                //two continuous end elements means we are exiting the xml structure
                                loopDone1 = true;
                            } else {
                                if (new javax.xml.namespace.QName("", "return").equals(
                                            reader.getName())) {
                                    list1.add(UserDTO.Factory.parse(reader));
                                } else {
                                    loopDone1 = true;
                                }
                            }
                        }

                        // call the converter utility  to convert and set the array
                        object.set_return((UserDTO[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                UserDTO.class, list1));
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class SaveProjectFromQYGE implements org.apache.axis2.databinding.ADBBean {
        public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://project.funnel.api.manage.hps.com/",
                "saveProjectFromQYG", "ns1");

         
        protected SaveProjectFromQYG localSaveProjectFromQYG;

         
        public SaveProjectFromQYG getSaveProjectFromQYG() {
            return localSaveProjectFromQYG;
        }

         
        public void setSaveProjectFromQYG(SaveProjectFromQYG param) {
            this.localSaveProjectFromQYG = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, MY_QNAME));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            //We can safely assume an element has only one type associated with it
            if (localSaveProjectFromQYG == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "saveProjectFromQYG cannot be null!");
            }

            localSaveProjectFromQYG.serialize(MY_QNAME, xmlWriter);
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static SaveProjectFromQYGE parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                SaveProjectFromQYGE object = new SaveProjectFromQYGE();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    while (!reader.isEndElement()) {
                        if (reader.isStartElement()) {
                            if (reader.isStartElement() &&
                                    new javax.xml.namespace.QName(
                                        "http://project.funnel.api.manage.hps.com/",
                                        "saveProjectFromQYG").equals(
                                        reader.getName())) {
                                object.setSaveProjectFromQYG(SaveProjectFromQYG.Factory.parse(
                                        reader));
                            } // End of if for expected property start element

                            else {
                                // 3 - A start element we are not expecting indicates an invalid parameter was passed
                                throw new org.apache.axis2.databinding.ADBException(
                                    "Unexpected subelement " +
                                    reader.getName());
                            }
                        } else {
                            reader.next();
                        }
                    } // end of while loop
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class SaveProjectFromQYGResponse implements org.apache.axis2.databinding.ADBBean {
         

         
        protected java.lang.String local_return;

         
        protected boolean local_returnTracker = false;

        public boolean is_returnSpecified() {
            return local_returnTracker;
        }

         
        public java.lang.String get_return() {
            return local_return;
        }

         
        public void set_return(java.lang.String param) {
            local_returnTracker = param != null;

            this.local_return = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            if (serializeType) {
                java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                        "http://project.funnel.api.manage.hps.com/");

                if ((namespacePrefix != null) &&
                        (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        namespacePrefix + ":saveProjectFromQYGResponse",
                        xmlWriter);
                } else {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "saveProjectFromQYGResponse", xmlWriter);
                }
            }

            if (local_returnTracker) {
                namespace = "";
                writeStartElement(null, namespace, "return", xmlWriter);

                if (local_return == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "return cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(local_return);
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static SaveProjectFromQYGResponse parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                SaveProjectFromQYGResponse object = new SaveProjectFromQYGResponse();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"saveProjectFromQYGResponse".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (SaveProjectFromQYGResponse) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "return").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "return" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class QueryProjectManagerFromHPS implements org.apache.axis2.databinding.ADBBean {
         

         
        protected ProjectROneSaveParam localArg0;

         
        protected boolean localArg0Tracker = false;

        public boolean isArg0Specified() {
            return localArg0Tracker;
        }

         
        public ProjectROneSaveParam getArg0() {
            return localArg0;
        }

         
        public void setArg0(ProjectROneSaveParam param) {
            localArg0Tracker = param != null;

            this.localArg0 = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            if (serializeType) {
                java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                        "http://project.funnel.api.manage.hps.com/");

                if ((namespacePrefix != null) &&
                        (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        namespacePrefix + ":queryProjectManagerFromHPS",
                        xmlWriter);
                } else {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "queryProjectManagerFromHPS", xmlWriter);
                }
            }

            if (localArg0Tracker) {
                if (localArg0 == null) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "arg0 cannot be null!!");
                }

                localArg0.serialize(new javax.xml.namespace.QName("", "arg0"),
                    xmlWriter);
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static QueryProjectManagerFromHPS parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                QueryProjectManagerFromHPS object = new QueryProjectManagerFromHPS();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"queryProjectManagerFromHPS".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (QueryProjectManagerFromHPS) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "arg0").equals(
                                reader.getName())) {
                        object.setArg0(ProjectROneSaveParam.Factory.parse(
                                reader));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class QueryProjectManagerFromHPSResponseE implements org.apache.axis2.databinding.ADBBean {
        public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://project.funnel.api.manage.hps.com/",
                "queryProjectManagerFromHPSResponse", "ns1");

         
        protected QueryProjectManagerFromHPSResponse localQueryProjectManagerFromHPSResponse;

         
        public QueryProjectManagerFromHPSResponse getQueryProjectManagerFromHPSResponse() {
            return localQueryProjectManagerFromHPSResponse;
        }

         
        public void setQueryProjectManagerFromHPSResponse(
            QueryProjectManagerFromHPSResponse param) {
            this.localQueryProjectManagerFromHPSResponse = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, MY_QNAME));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            //We can safely assume an element has only one type associated with it
            if (localQueryProjectManagerFromHPSResponse == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "queryProjectManagerFromHPSResponse cannot be null!");
            }

            localQueryProjectManagerFromHPSResponse.serialize(MY_QNAME,
                xmlWriter);
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static QueryProjectManagerFromHPSResponseE parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                QueryProjectManagerFromHPSResponseE object = new QueryProjectManagerFromHPSResponseE();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    while (!reader.isEndElement()) {
                        if (reader.isStartElement()) {
                            if (reader.isStartElement() &&
                                    new javax.xml.namespace.QName(
                                        "http://project.funnel.api.manage.hps.com/",
                                        "queryProjectManagerFromHPSResponse").equals(
                                        reader.getName())) {
                                object.setQueryProjectManagerFromHPSResponse(QueryProjectManagerFromHPSResponse.Factory.parse(
                                        reader));
                            } // End of if for expected property start element

                            else {
                                // 3 - A start element we are not expecting indicates an invalid parameter was passed
                                throw new org.apache.axis2.databinding.ADBException(
                                    "Unexpected subelement " +
                                    reader.getName());
                            }
                        } else {
                            reader.next();
                        }
                    } // end of while loop
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class ToString implements org.apache.axis2.databinding.ADBBean {
         

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            if (serializeType) {
                java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                        "http://project.funnel.api.manage.hps.com/");

                if ((namespacePrefix != null) &&
                        (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        namespacePrefix + ":toString", xmlWriter);
                } else {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "toString", xmlWriter);
                }
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static ToString parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                ToString object = null;

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"toString".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (ToString) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }

                            throw new org.apache.axis2.databinding.ADBException(
                                "The an abstract class can not be instantiated !!!");
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class UserDTO extends BasicDto implements org.apache.axis2.databinding.ADBBean {
         

         
        protected java.util.Calendar localActiveBeginTime;

         
        protected boolean localActiveBeginTimeTracker = false;

         
        protected java.util.Calendar localActiveEndTime;

         
        protected boolean localActiveEndTimeTracker = false;

         
        protected boolean localBeLocked;

         
        protected boolean localBeLockedTracker = false;

         
        protected boolean localBeSystemManage;

         
        protected boolean localBeSystemManageTracker = false;

         
        protected java.lang.String localCode;

         
        protected boolean localCodeTracker = false;

         
        protected java.lang.String localCreatedById;

         
        protected boolean localCreatedByIdTracker = false;

         
        protected java.lang.String localDeptName;

         
        protected boolean localDeptNameTracker = false;

         
        protected java.lang.String localDeptNo;

         
        protected boolean localDeptNoTracker = false;

         
        protected java.lang.String localDomainCode;

         
        protected boolean localDomainCodeTracker = false;

         
        protected java.lang.String localDomainName;

         
        protected boolean localDomainNameTracker = false;

         
        protected java.lang.String localEmail;

         
        protected boolean localEmailTracker = false;

         
        protected java.lang.String localFax;

         
        protected boolean localFaxTracker = false;

         
        protected java.lang.String localFirstLineCode;

         
        protected boolean localFirstLineCodeTracker = false;

         
        protected java.lang.String localFirstLineName;

         
        protected boolean localFirstLineNameTracker = false;

         
        protected java.lang.String localId;

         
        protected boolean localIdTracker = false;

         
        protected java.lang.String localImgPath;

         
        protected boolean localImgPathTracker = false;

         
        protected java.lang.String localIsManage;

         
        protected boolean localIsManageTracker = false;

         
        protected java.util.Calendar localLastLoginTime;

         
        protected boolean localLastLoginTimeTracker = false;

         
        protected java.lang.String localLastModifiedById;

         
        protected boolean localLastModifiedByIdTracker = false;

         
        protected java.lang.String localLockReason;

         
        protected boolean localLockReasonTracker = false;

         
        protected java.lang.String localMobile;

         
        protected boolean localMobileTracker = false;

         
        protected java.lang.String localName;

         
        protected boolean localNameTracker = false;

         
        protected java.lang.String localOpenid;

         
        protected boolean localOpenidTracker = false;

         
        protected java.lang.String localOrganizationCode;

         
        protected boolean localOrganizationCodeTracker = false;

         
        protected java.lang.String localOrganizationName;

         
        protected boolean localOrganizationNameTracker = false;

         
        protected java.lang.String localPhone;

         
        protected boolean localPhoneTracker = false;

         
        protected java.lang.String localRegistTime;

         
        protected boolean localRegistTimeTracker = false;

         
        protected java.lang.String localRemark;

         
        protected boolean localRemarkTracker = false;

         
        protected java.lang.String localSecondLineCode;

         
        protected boolean localSecondLineCodeTracker = false;

         
        protected java.lang.String localSecondLineName;

         
        protected boolean localSecondLineNameTracker = false;

         
        protected java.lang.String localSex;

         
        protected boolean localSexTracker = false;

         
        protected java.lang.String localStandardPostCode;

         
        protected boolean localStandardPostCodeTracker = false;

         
        protected java.lang.String localStandardPostName;

         
        protected boolean localStandardPostNameTracker = false;

         
        protected BasePostTagContentDTO[] localTagInfo;

         
        protected boolean localTagInfoTracker = false;

        public boolean isActiveBeginTimeSpecified() {
            return localActiveBeginTimeTracker;
        }

         
        public java.util.Calendar getActiveBeginTime() {
            return localActiveBeginTime;
        }

         
        public void setActiveBeginTime(java.util.Calendar param) {
            localActiveBeginTimeTracker = param != null;

            this.localActiveBeginTime = param;
        }

        public boolean isActiveEndTimeSpecified() {
            return localActiveEndTimeTracker;
        }

         
        public java.util.Calendar getActiveEndTime() {
            return localActiveEndTime;
        }

         
        public void setActiveEndTime(java.util.Calendar param) {
            localActiveEndTimeTracker = param != null;

            this.localActiveEndTime = param;
        }

        public boolean isBeLockedSpecified() {
            return localBeLockedTracker;
        }

         
        public boolean getBeLocked() {
            return localBeLocked;
        }

         
        public void setBeLocked(boolean param) {
            // setting primitive attribute tracker to true
            localBeLockedTracker = true;

            this.localBeLocked = param;
        }

        public boolean isBeSystemManageSpecified() {
            return localBeSystemManageTracker;
        }

         
        public boolean getBeSystemManage() {
            return localBeSystemManage;
        }

         
        public void setBeSystemManage(boolean param) {
            // setting primitive attribute tracker to true
            localBeSystemManageTracker = true;

            this.localBeSystemManage = param;
        }

        public boolean isCodeSpecified() {
            return localCodeTracker;
        }

         
        public java.lang.String getCode() {
            return localCode;
        }

         
        public void setCode(java.lang.String param) {
            localCodeTracker = param != null;

            this.localCode = param;
        }

        public boolean isCreatedByIdSpecified() {
            return localCreatedByIdTracker;
        }

         
        public java.lang.String getCreatedById() {
            return localCreatedById;
        }

         
        public void setCreatedById(java.lang.String param) {
            localCreatedByIdTracker = param != null;

            this.localCreatedById = param;
        }

        public boolean isDeptNameSpecified() {
            return localDeptNameTracker;
        }

         
        public java.lang.String getDeptName() {
            return localDeptName;
        }

         
        public void setDeptName(java.lang.String param) {
            localDeptNameTracker = param != null;

            this.localDeptName = param;
        }

        public boolean isDeptNoSpecified() {
            return localDeptNoTracker;
        }

         
        public java.lang.String getDeptNo() {
            return localDeptNo;
        }

         
        public void setDeptNo(java.lang.String param) {
            localDeptNoTracker = param != null;

            this.localDeptNo = param;
        }

        public boolean isDomainCodeSpecified() {
            return localDomainCodeTracker;
        }

         
        public java.lang.String getDomainCode() {
            return localDomainCode;
        }

         
        public void setDomainCode(java.lang.String param) {
            localDomainCodeTracker = param != null;

            this.localDomainCode = param;
        }

        public boolean isDomainNameSpecified() {
            return localDomainNameTracker;
        }

         
        public java.lang.String getDomainName() {
            return localDomainName;
        }

         
        public void setDomainName(java.lang.String param) {
            localDomainNameTracker = param != null;

            this.localDomainName = param;
        }

        public boolean isEmailSpecified() {
            return localEmailTracker;
        }

         
        public java.lang.String getEmail() {
            return localEmail;
        }

         
        public void setEmail(java.lang.String param) {
            localEmailTracker = param != null;

            this.localEmail = param;
        }

        public boolean isFaxSpecified() {
            return localFaxTracker;
        }

         
        public java.lang.String getFax() {
            return localFax;
        }

         
        public void setFax(java.lang.String param) {
            localFaxTracker = param != null;

            this.localFax = param;
        }

        public boolean isFirstLineCodeSpecified() {
            return localFirstLineCodeTracker;
        }

         
        public java.lang.String getFirstLineCode() {
            return localFirstLineCode;
        }

         
        public void setFirstLineCode(java.lang.String param) {
            localFirstLineCodeTracker = param != null;

            this.localFirstLineCode = param;
        }

        public boolean isFirstLineNameSpecified() {
            return localFirstLineNameTracker;
        }

         
        public java.lang.String getFirstLineName() {
            return localFirstLineName;
        }

         
        public void setFirstLineName(java.lang.String param) {
            localFirstLineNameTracker = param != null;

            this.localFirstLineName = param;
        }

        public boolean isIdSpecified() {
            return localIdTracker;
        }

         
        public java.lang.String getId() {
            return localId;
        }

         
        public void setId(java.lang.String param) {
            localIdTracker = param != null;

            this.localId = param;
        }

        public boolean isImgPathSpecified() {
            return localImgPathTracker;
        }

         
        public java.lang.String getImgPath() {
            return localImgPath;
        }

         
        public void setImgPath(java.lang.String param) {
            localImgPathTracker = param != null;

            this.localImgPath = param;
        }

        public boolean isIsManageSpecified() {
            return localIsManageTracker;
        }

         
        public java.lang.String getIsManage() {
            return localIsManage;
        }

         
        public void setIsManage(java.lang.String param) {
            localIsManageTracker = param != null;

            this.localIsManage = param;
        }

        public boolean isLastLoginTimeSpecified() {
            return localLastLoginTimeTracker;
        }

         
        public java.util.Calendar getLastLoginTime() {
            return localLastLoginTime;
        }

         
        public void setLastLoginTime(java.util.Calendar param) {
            localLastLoginTimeTracker = param != null;

            this.localLastLoginTime = param;
        }

        public boolean isLastModifiedByIdSpecified() {
            return localLastModifiedByIdTracker;
        }

         
        public java.lang.String getLastModifiedById() {
            return localLastModifiedById;
        }

         
        public void setLastModifiedById(java.lang.String param) {
            localLastModifiedByIdTracker = param != null;

            this.localLastModifiedById = param;
        }

        public boolean isLockReasonSpecified() {
            return localLockReasonTracker;
        }

         
        public java.lang.String getLockReason() {
            return localLockReason;
        }

         
        public void setLockReason(java.lang.String param) {
            localLockReasonTracker = param != null;

            this.localLockReason = param;
        }

        public boolean isMobileSpecified() {
            return localMobileTracker;
        }

         
        public java.lang.String getMobile() {
            return localMobile;
        }

         
        public void setMobile(java.lang.String param) {
            localMobileTracker = param != null;

            this.localMobile = param;
        }

        public boolean isNameSpecified() {
            return localNameTracker;
        }

         
        public java.lang.String getName() {
            return localName;
        }

         
        public void setName(java.lang.String param) {
            localNameTracker = param != null;

            this.localName = param;
        }

        public boolean isOpenidSpecified() {
            return localOpenidTracker;
        }

         
        public java.lang.String getOpenid() {
            return localOpenid;
        }

         
        public void setOpenid(java.lang.String param) {
            localOpenidTracker = param != null;

            this.localOpenid = param;
        }

        public boolean isOrganizationCodeSpecified() {
            return localOrganizationCodeTracker;
        }

         
        public java.lang.String getOrganizationCode() {
            return localOrganizationCode;
        }

         
        public void setOrganizationCode(java.lang.String param) {
            localOrganizationCodeTracker = param != null;

            this.localOrganizationCode = param;
        }

        public boolean isOrganizationNameSpecified() {
            return localOrganizationNameTracker;
        }

         
        public java.lang.String getOrganizationName() {
            return localOrganizationName;
        }

         
        public void setOrganizationName(java.lang.String param) {
            localOrganizationNameTracker = param != null;

            this.localOrganizationName = param;
        }

        public boolean isPhoneSpecified() {
            return localPhoneTracker;
        }

         
        public java.lang.String getPhone() {
            return localPhone;
        }

         
        public void setPhone(java.lang.String param) {
            localPhoneTracker = param != null;

            this.localPhone = param;
        }

        public boolean isRegistTimeSpecified() {
            return localRegistTimeTracker;
        }

         
        public java.lang.String getRegistTime() {
            return localRegistTime;
        }

         
        public void setRegistTime(java.lang.String param) {
            localRegistTimeTracker = param != null;

            this.localRegistTime = param;
        }

        public boolean isRemarkSpecified() {
            return localRemarkTracker;
        }

         
        public java.lang.String getRemark() {
            return localRemark;
        }

         
        public void setRemark(java.lang.String param) {
            localRemarkTracker = param != null;

            this.localRemark = param;
        }

        public boolean isSecondLineCodeSpecified() {
            return localSecondLineCodeTracker;
        }

         
        public java.lang.String getSecondLineCode() {
            return localSecondLineCode;
        }

         
        public void setSecondLineCode(java.lang.String param) {
            localSecondLineCodeTracker = param != null;

            this.localSecondLineCode = param;
        }

        public boolean isSecondLineNameSpecified() {
            return localSecondLineNameTracker;
        }

         
        public java.lang.String getSecondLineName() {
            return localSecondLineName;
        }

         
        public void setSecondLineName(java.lang.String param) {
            localSecondLineNameTracker = param != null;

            this.localSecondLineName = param;
        }

        public boolean isSexSpecified() {
            return localSexTracker;
        }

         
        public java.lang.String getSex() {
            return localSex;
        }

         
        public void setSex(java.lang.String param) {
            localSexTracker = param != null;

            this.localSex = param;
        }

        public boolean isStandardPostCodeSpecified() {
            return localStandardPostCodeTracker;
        }

         
        public java.lang.String getStandardPostCode() {
            return localStandardPostCode;
        }

         
        public void setStandardPostCode(java.lang.String param) {
            localStandardPostCodeTracker = param != null;

            this.localStandardPostCode = param;
        }

        public boolean isStandardPostNameSpecified() {
            return localStandardPostNameTracker;
        }

         
        public java.lang.String getStandardPostName() {
            return localStandardPostName;
        }

         
        public void setStandardPostName(java.lang.String param) {
            localStandardPostNameTracker = param != null;

            this.localStandardPostName = param;
        }

        public boolean isTagInfoSpecified() {
            return localTagInfoTracker;
        }

         
        public BasePostTagContentDTO[] getTagInfo() {
            return localTagInfo;
        }

         
        protected void validateTagInfo(BasePostTagContentDTO[] param) {
        }

         
        public void setTagInfo(BasePostTagContentDTO[] param) {
            validateTagInfo(param);

            localTagInfoTracker = true;

            this.localTagInfo = param;
        }

         
        public void addTagInfo(BasePostTagContentDTO param) {
            if (localTagInfo == null) {
                localTagInfo = new BasePostTagContentDTO[] {  };
            }

            //update the setting tracker
            localTagInfoTracker = true;

            java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localTagInfo);
            list.add(param);
            this.localTagInfo = (BasePostTagContentDTO[]) list.toArray(new BasePostTagContentDTO[list.size()]);
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://project.funnel.api.manage.hps.com/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":userDTO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "userDTO", xmlWriter);
            }

            if (localBatchDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "batchDate", xmlWriter);

                if (localBatchDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "batchDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBatchDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localCreProIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "creProId", xmlWriter);

                if (localCreProId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "creProId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreProId);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdBy", xmlWriter);

                if (localCreatedBy == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdBy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedBy);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdDate", xmlWriter);

                if (localCreatedDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localCreatedDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localDeletedTracker) {
                namespace = "";
                writeStartElement(null, namespace, "deleted", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "deleted cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localDeleted));
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedBy", xmlWriter);

                if (localLastModifiedBy == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedBy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedBy);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedDate", xmlWriter);

                if (localLastModifiedDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localLastModifiedDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localModProIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "modProId", xmlWriter);

                if (localModProId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "modProId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localModProId);
                }

                xmlWriter.writeEndElement();
            }

            if (localActiveBeginTimeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "activeBeginTime", xmlWriter);

                if (localActiveBeginTime == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "activeBeginTime cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localActiveBeginTime));
                }

                xmlWriter.writeEndElement();
            }

            if (localActiveEndTimeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "activeEndTime", xmlWriter);

                if (localActiveEndTime == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "activeEndTime cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localActiveEndTime));
                }

                xmlWriter.writeEndElement();
            }

            if (localBeLockedTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beLocked", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beLocked cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeLocked));
                }

                xmlWriter.writeEndElement();
            }

            if (localBeSystemManageTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beSystemManage", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beSystemManage cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeSystemManage));
                }

                xmlWriter.writeEndElement();
            }

            if (localCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "code", xmlWriter);

                if (localCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "code cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdById", xmlWriter);

                if (localCreatedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localDeptNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "deptName", xmlWriter);

                if (localDeptName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "deptName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDeptName);
                }

                xmlWriter.writeEndElement();
            }

            if (localDeptNoTracker) {
                namespace = "";
                writeStartElement(null, namespace, "deptNo", xmlWriter);

                if (localDeptNo == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "deptNo cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDeptNo);
                }

                xmlWriter.writeEndElement();
            }

            if (localDomainCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "domainCode", xmlWriter);

                if (localDomainCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "domainCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDomainCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localDomainNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "domainName", xmlWriter);

                if (localDomainName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "domainName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDomainName);
                }

                xmlWriter.writeEndElement();
            }

            if (localEmailTracker) {
                namespace = "";
                writeStartElement(null, namespace, "email", xmlWriter);

                if (localEmail == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "email cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localEmail);
                }

                xmlWriter.writeEndElement();
            }

            if (localFaxTracker) {
                namespace = "";
                writeStartElement(null, namespace, "fax", xmlWriter);

                if (localFax == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "fax cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localFax);
                }

                xmlWriter.writeEndElement();
            }

            if (localFirstLineCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "firstLineCode", xmlWriter);

                if (localFirstLineCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "firstLineCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localFirstLineCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localFirstLineNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "firstLineName", xmlWriter);

                if (localFirstLineName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "firstLineName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localFirstLineName);
                }

                xmlWriter.writeEndElement();
            }

            if (localIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "id", xmlWriter);

                if (localId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "id cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localId);
                }

                xmlWriter.writeEndElement();
            }

            if (localImgPathTracker) {
                namespace = "";
                writeStartElement(null, namespace, "imgPath", xmlWriter);

                if (localImgPath == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "imgPath cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localImgPath);
                }

                xmlWriter.writeEndElement();
            }

            if (localIsManageTracker) {
                namespace = "";
                writeStartElement(null, namespace, "isManage", xmlWriter);

                if (localIsManage == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "isManage cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localIsManage);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastLoginTimeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastLoginTime", xmlWriter);

                if (localLastLoginTime == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastLoginTime cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localLastLoginTime));
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedById", xmlWriter);

                if (localLastModifiedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localLockReasonTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lockReason", xmlWriter);

                if (localLockReason == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lockReason cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLockReason);
                }

                xmlWriter.writeEndElement();
            }

            if (localMobileTracker) {
                namespace = "";
                writeStartElement(null, namespace, "mobile", xmlWriter);

                if (localMobile == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "mobile cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localMobile);
                }

                xmlWriter.writeEndElement();
            }

            if (localNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "name", xmlWriter);

                if (localName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "name cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localName);
                }

                xmlWriter.writeEndElement();
            }

            if (localOpenidTracker) {
                namespace = "";
                writeStartElement(null, namespace, "openid", xmlWriter);

                if (localOpenid == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "openid cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localOpenid);
                }

                xmlWriter.writeEndElement();
            }

            if (localOrganizationCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "organizationCode", xmlWriter);

                if (localOrganizationCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "organizationCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localOrganizationCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localOrganizationNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "organizationName", xmlWriter);

                if (localOrganizationName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "organizationName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localOrganizationName);
                }

                xmlWriter.writeEndElement();
            }

            if (localPhoneTracker) {
                namespace = "";
                writeStartElement(null, namespace, "phone", xmlWriter);

                if (localPhone == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "phone cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localPhone);
                }

                xmlWriter.writeEndElement();
            }

            if (localRegistTimeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "registTime", xmlWriter);

                if (localRegistTime == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "registTime cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localRegistTime);
                }

                xmlWriter.writeEndElement();
            }

            if (localRemarkTracker) {
                namespace = "";
                writeStartElement(null, namespace, "remark", xmlWriter);

                if (localRemark == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "remark cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localRemark);
                }

                xmlWriter.writeEndElement();
            }

            if (localSecondLineCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "secondLineCode", xmlWriter);

                if (localSecondLineCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "secondLineCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localSecondLineCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localSecondLineNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "secondLineName", xmlWriter);

                if (localSecondLineName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "secondLineName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localSecondLineName);
                }

                xmlWriter.writeEndElement();
            }

            if (localSexTracker) {
                namespace = "";
                writeStartElement(null, namespace, "sex", xmlWriter);

                if (localSex == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "sex cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localSex);
                }

                xmlWriter.writeEndElement();
            }

            if (localStandardPostCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "standardPostCode", xmlWriter);

                if (localStandardPostCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "standardPostCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localStandardPostCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localStandardPostNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "standardPostName", xmlWriter);

                if (localStandardPostName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "standardPostName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localStandardPostName);
                }

                xmlWriter.writeEndElement();
            }

            if (localTagInfoTracker) {
                if (localTagInfo != null) {
                    for (int i = 0; i < localTagInfo.length; i++) {
                        if (localTagInfo[i] != null) {
                            localTagInfo[i].serialize(new javax.xml.namespace.QName(
                                    "", "tagInfo"), xmlWriter);
                        } else {
                            writeStartElement(null, "", "tagInfo", xmlWriter);

                            // write the nil attribute
                            writeAttribute("xsi",
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "nil", "1", xmlWriter);
                            xmlWriter.writeEndElement();
                        }
                    }
                } else {
                    writeStartElement(null, "", "tagInfo", xmlWriter);

                    // write the nil attribute
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "nil",
                        "1", xmlWriter);
                    xmlWriter.writeEndElement();
                }
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static UserDTO parse(javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                UserDTO object = new UserDTO();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"userDTO".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (UserDTO) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    java.util.ArrayList list42 = new java.util.ArrayList();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "batchDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "batchDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBatchDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "creProId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "creProId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreProId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdBy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdBy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedBy(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "deleted").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "deleted" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDeleted(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedBy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedBy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedBy(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "modProId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "modProId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setModProId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "activeBeginTime").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "activeBeginTime" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setActiveBeginTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "activeEndTime").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "activeEndTime" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setActiveEndTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beLocked").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beLocked" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeLocked(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beSystemManage").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beSystemManage" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeSystemManage(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "code").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "code" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "deptName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "deptName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDeptName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "deptNo").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "deptNo" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDeptNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "domainCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "domainCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDomainCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "domainName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "domainName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDomainName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "email").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "email" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setEmail(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "fax").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "fax" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFax(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "firstLineCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "firstLineCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFirstLineCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "firstLineName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "firstLineName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFirstLineName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "id").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "id" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "imgPath").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "imgPath" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setImgPath(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "isManage").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "isManage" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setIsManage(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastLoginTime").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastLoginTime" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastLoginTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lockReason").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lockReason" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLockReason(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "mobile").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "mobile" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setMobile(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "name").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "name" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "openid").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "openid" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setOpenid(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "organizationCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "organizationCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setOrganizationCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "organizationName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "organizationName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setOrganizationName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "phone").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "phone" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setPhone(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "registTime").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "registTime" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setRegistTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "remark").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "remark" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setRemark(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "secondLineCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "secondLineCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setSecondLineCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "secondLineName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "secondLineName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setSecondLineName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "sex").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "sex" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setSex(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "standardPostCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "standardPostCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setStandardPostCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "standardPostName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "standardPostName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setStandardPostName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "tagInfo").equals(
                                reader.getName())) {
                        // Process the array and step past its final element's end.
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            list42.add(null);
                            reader.next();
                        } else {
                            list42.add(BasePostTagContentDTO.Factory.parse(
                                    reader));
                        }

                        //loop until we find a start element that is not part of this array
                        boolean loopDone42 = false;

                        while (!loopDone42) {
                            // We should be at the end element, but make sure
                            while (!reader.isEndElement())
                                reader.next();

                            // Step out of this element
                            reader.next();

                            // Step to next element event.
                            while (!reader.isStartElement() &&
                                    !reader.isEndElement())
                                reader.next();

                            if (reader.isEndElement()) {
                                //two continuous end elements means we are exiting the xml structure
                                loopDone42 = true;
                            } else {
                                if (new javax.xml.namespace.QName("", "tagInfo").equals(
                                            reader.getName())) {
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                            "nil");

                                    if ("true".equals(nillableValue) ||
                                            "1".equals(nillableValue)) {
                                        list42.add(null);
                                        reader.next();
                                    } else {
                                        list42.add(BasePostTagContentDTO.Factory.parse(
                                                reader));
                                    }
                                } else {
                                    loopDone42 = true;
                                }
                            }
                        }

                        // call the converter utility  to convert and set the array
                        object.setTagInfo((BasePostTagContentDTO[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                BasePostTagContentDTO.class, list42));
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class ProjectROneSaveParam extends ToString implements org.apache.axis2.databinding.ADBBean {
         

         
        protected java.lang.String localAddressCity;

         
        protected boolean localAddressCityTracker = false;

         
        protected java.lang.String localAddressCounty;

         
        protected boolean localAddressCountyTracker = false;

         
        protected java.lang.String localAddressDetail;

         
        protected boolean localAddressDetailTracker = false;

         
        protected java.lang.String localAddressProvince;

         
        protected boolean localAddressProvinceTracker = false;

         
        protected java.lang.String localArea;

         
        protected boolean localAreaTracker = false;

         
        protected boolean localBeAfterPurchase;

         
        protected boolean localBeAfterPurchaseTracker = false;

         
        protected boolean localBeGroupBuy;

         
        protected boolean localBeGroupBuyTracker = false;

         
        protected boolean localBeImgageProject;

         
        protected boolean localBeImgageProjectTracker = false;

         
        protected boolean localBeManualSelectProjectFinish;

         
        protected boolean localBeManualSelectProjectFinishTracker = false;

         
        protected java.lang.String localBeSendGpms;

         
        protected boolean localBeSendGpmsTracker = false;

         
        protected boolean localBeStrategy;

         
        protected boolean localBeStrategyTracker = false;

         
        protected boolean localBeTurnStraightGuest;

         
        protected boolean localBeTurnStraightGuestTracker = false;

         
        protected java.lang.String localCancelPersonCode;

         
        protected boolean localCancelPersonCodeTracker = false;

         
        protected java.lang.String localCancelReason;

         
        protected boolean localCancelReasonTracker = false;

         
        protected java.util.Calendar localCancelTime;

         
        protected boolean localCancelTimeTracker = false;

         
        protected java.lang.String localCenter;

         
        protected boolean localCenterTracker = false;

         
        protected java.lang.String localCleanEnergy;

         
        protected boolean localCleanEnergyTracker = false;

         
        protected java.lang.String localCleanEnergyType;

         
        protected boolean localCleanEnergyTypeTracker = false;

         
        protected java.lang.String localCreatedById;

         
        protected boolean localCreatedByIdTracker = false;

         
        protected java.lang.String localDataSource;

         
        protected boolean localDataSourceTracker = false;

         
        protected java.lang.String localDistrictCode;

         
        protected boolean localDistrictCodeTracker = false;

         
        protected java.lang.String localDomainModel;

         
        protected boolean localDomainModelTracker = false;

         
        protected java.lang.String localDomainType;

         
        protected boolean localDomainTypeTracker = false;

         
        protected java.util.Calendar localEstimatedTimeBid;

         
        protected boolean localEstimatedTimeBidTracker = false;

         
        protected java.lang.String localFirstCompanyId;

         
        protected boolean localFirstCompanyIdTracker = false;

         
        protected java.lang.String localFirstCompanyName;

         
        protected boolean localFirstCompanyNameTracker = false;

         
        protected java.lang.String localFirstCompanyOrgCode;

         
        protected boolean localFirstCompanyOrgCodeTracker = false;

         
        protected boolean localFrequencyConversion;

         
        protected boolean localFrequencyConversionTracker = false;

         
        protected java.lang.String localFunnelStage;

         
        protected boolean localFunnelStageTracker = false;

         
        protected java.util.Calendar localFunnelStageUpdateTime;

         
        protected boolean localFunnelStageUpdateTimeTracker = false;

         
        protected java.lang.String localGridCenterCode;

         
        protected boolean localGridCenterCodeTracker = false;

         
        protected java.lang.String localGridCenterName;

         
        protected boolean localGridCenterNameTracker = false;

         
        protected java.lang.String localGridCode;

         
        protected boolean localGridCodeTracker = false;

         
        protected java.lang.String localGridName;

         
        protected boolean localGridNameTracker = false;

         
        protected java.lang.String localGridUserCode;

         
        protected boolean localGridUserCodeTracker = false;

         
        protected java.lang.String localGridUserName;

         
        protected boolean localGridUserNameTracker = false;

         
        protected java.lang.String localId;

         
        protected boolean localIdTracker = false;

         
        protected java.lang.String localLastModifiedById;

         
        protected boolean localLastModifiedByIdTracker = false;

         
        protected java.lang.String localLesFlag;

         
        protected boolean localLesFlagTracker = false;

         
        protected java.lang.String localLesMsg;

         
        protected boolean localLesMsgTracker = false;

         
        protected int localLoginLevel;

         
        protected boolean localLoginLevelTracker = false;

         
        protected int localMsgId;

         
        protected boolean localMsgIdTracker = false;

         
        protected java.lang.String localProjectCode;

         
        protected boolean localProjectCodeTracker = false;

         
        protected java.lang.String localProjectCreaterCode;

         
        protected boolean localProjectCreaterCodeTracker = false;

         
        protected java.lang.String localProjectCreaterId;

         
        protected boolean localProjectCreaterIdTracker = false;

         
        protected java.lang.String localProjectCreaterName;

         
        protected boolean localProjectCreaterNameTracker = false;

         
        protected java.util.Calendar localProjectFinishDate;

         
        protected boolean localProjectFinishDateTracker = false;

         
        protected java.util.Calendar localProjectLastModifiedDate;

         
        protected boolean localProjectLastModifiedDateTracker = false;

         
        protected java.lang.String localProjectManagerCenter;

         
        protected boolean localProjectManagerCenterTracker = false;

         
        protected java.lang.String localProjectManagerCode;

         
        protected boolean localProjectManagerCodeTracker = false;

         
        protected java.lang.String localProjectManagerId;

         
        protected boolean localProjectManagerIdTracker = false;

         
        protected java.lang.String localProjectManagerName;

         
        protected boolean localProjectManagerNameTracker = false;

         
        protected java.lang.String localProjectName;

         
        protected boolean localProjectNameTracker = false;

         
        protected java.lang.String localProjectSource;

         
        protected boolean localProjectSourceTracker = false;

         
        protected java.lang.String localProjectStatus;

         
        protected boolean localProjectStatusTracker = false;

         
        protected java.lang.String localProjectType;

         
        protected boolean localProjectTypeTracker = false;

         
        protected ProjectPurchaseForecastSaveParam[] localPurchaseForecastList;

         
        protected boolean localPurchaseForecastListTracker = false;

         
        protected java.lang.String localSalesModel;

         
        protected boolean localSalesModelTracker = false;

         
        protected java.lang.String localSamplePlateType;

         
        protected boolean localSamplePlateTypeTracker = false;

         
        protected java.lang.String localSendGpmsResult;

         
        protected boolean localSendGpmsResultTracker = false;

         
        protected java.util.Calendar localSendGpmsTime;

         
        protected boolean localSendGpmsTimeTracker = false;

        public boolean isAddressCitySpecified() {
            return localAddressCityTracker;
        }

         
        public java.lang.String getAddressCity() {
            return localAddressCity;
        }

         
        public void setAddressCity(java.lang.String param) {
            localAddressCityTracker = param != null;

            this.localAddressCity = param;
        }

        public boolean isAddressCountySpecified() {
            return localAddressCountyTracker;
        }

         
        public java.lang.String getAddressCounty() {
            return localAddressCounty;
        }

         
        public void setAddressCounty(java.lang.String param) {
            localAddressCountyTracker = param != null;

            this.localAddressCounty = param;
        }

        public boolean isAddressDetailSpecified() {
            return localAddressDetailTracker;
        }

         
        public java.lang.String getAddressDetail() {
            return localAddressDetail;
        }

         
        public void setAddressDetail(java.lang.String param) {
            localAddressDetailTracker = param != null;

            this.localAddressDetail = param;
        }

        public boolean isAddressProvinceSpecified() {
            return localAddressProvinceTracker;
        }

         
        public java.lang.String getAddressProvince() {
            return localAddressProvince;
        }

         
        public void setAddressProvince(java.lang.String param) {
            localAddressProvinceTracker = param != null;

            this.localAddressProvince = param;
        }

        public boolean isAreaSpecified() {
            return localAreaTracker;
        }

         
        public java.lang.String getArea() {
            return localArea;
        }

         
        public void setArea(java.lang.String param) {
            localAreaTracker = param != null;

            this.localArea = param;
        }

        public boolean isBeAfterPurchaseSpecified() {
            return localBeAfterPurchaseTracker;
        }

         
        public boolean getBeAfterPurchase() {
            return localBeAfterPurchase;
        }

         
        public void setBeAfterPurchase(boolean param) {
            // setting primitive attribute tracker to true
            localBeAfterPurchaseTracker = true;

            this.localBeAfterPurchase = param;
        }

        public boolean isBeGroupBuySpecified() {
            return localBeGroupBuyTracker;
        }

         
        public boolean getBeGroupBuy() {
            return localBeGroupBuy;
        }

         
        public void setBeGroupBuy(boolean param) {
            // setting primitive attribute tracker to true
            localBeGroupBuyTracker = true;

            this.localBeGroupBuy = param;
        }

        public boolean isBeImgageProjectSpecified() {
            return localBeImgageProjectTracker;
        }

         
        public boolean getBeImgageProject() {
            return localBeImgageProject;
        }

         
        public void setBeImgageProject(boolean param) {
            // setting primitive attribute tracker to true
            localBeImgageProjectTracker = true;

            this.localBeImgageProject = param;
        }

        public boolean isBeManualSelectProjectFinishSpecified() {
            return localBeManualSelectProjectFinishTracker;
        }

         
        public boolean getBeManualSelectProjectFinish() {
            return localBeManualSelectProjectFinish;
        }

         
        public void setBeManualSelectProjectFinish(boolean param) {
            // setting primitive attribute tracker to true
            localBeManualSelectProjectFinishTracker = true;

            this.localBeManualSelectProjectFinish = param;
        }

        public boolean isBeSendGpmsSpecified() {
            return localBeSendGpmsTracker;
        }

         
        public java.lang.String getBeSendGpms() {
            return localBeSendGpms;
        }

         
        public void setBeSendGpms(java.lang.String param) {
            localBeSendGpmsTracker = param != null;

            this.localBeSendGpms = param;
        }

        public boolean isBeStrategySpecified() {
            return localBeStrategyTracker;
        }

         
        public boolean getBeStrategy() {
            return localBeStrategy;
        }

         
        public void setBeStrategy(boolean param) {
            // setting primitive attribute tracker to true
            localBeStrategyTracker = true;

            this.localBeStrategy = param;
        }

        public boolean isBeTurnStraightGuestSpecified() {
            return localBeTurnStraightGuestTracker;
        }

         
        public boolean getBeTurnStraightGuest() {
            return localBeTurnStraightGuest;
        }

         
        public void setBeTurnStraightGuest(boolean param) {
            // setting primitive attribute tracker to true
            localBeTurnStraightGuestTracker = true;

            this.localBeTurnStraightGuest = param;
        }

        public boolean isCancelPersonCodeSpecified() {
            return localCancelPersonCodeTracker;
        }

         
        public java.lang.String getCancelPersonCode() {
            return localCancelPersonCode;
        }

         
        public void setCancelPersonCode(java.lang.String param) {
            localCancelPersonCodeTracker = param != null;

            this.localCancelPersonCode = param;
        }

        public boolean isCancelReasonSpecified() {
            return localCancelReasonTracker;
        }

         
        public java.lang.String getCancelReason() {
            return localCancelReason;
        }

         
        public void setCancelReason(java.lang.String param) {
            localCancelReasonTracker = param != null;

            this.localCancelReason = param;
        }

        public boolean isCancelTimeSpecified() {
            return localCancelTimeTracker;
        }

         
        public java.util.Calendar getCancelTime() {
            return localCancelTime;
        }

         
        public void setCancelTime(java.util.Calendar param) {
            localCancelTimeTracker = param != null;

            this.localCancelTime = param;
        }

        public boolean isCenterSpecified() {
            return localCenterTracker;
        }

         
        public java.lang.String getCenter() {
            return localCenter;
        }

         
        public void setCenter(java.lang.String param) {
            localCenterTracker = param != null;

            this.localCenter = param;
        }

        public boolean isCleanEnergySpecified() {
            return localCleanEnergyTracker;
        }

         
        public java.lang.String getCleanEnergy() {
            return localCleanEnergy;
        }

         
        public void setCleanEnergy(java.lang.String param) {
            localCleanEnergyTracker = param != null;

            this.localCleanEnergy = param;
        }

        public boolean isCleanEnergyTypeSpecified() {
            return localCleanEnergyTypeTracker;
        }

         
        public java.lang.String getCleanEnergyType() {
            return localCleanEnergyType;
        }

         
        public void setCleanEnergyType(java.lang.String param) {
            localCleanEnergyTypeTracker = param != null;

            this.localCleanEnergyType = param;
        }

        public boolean isCreatedByIdSpecified() {
            return localCreatedByIdTracker;
        }

         
        public java.lang.String getCreatedById() {
            return localCreatedById;
        }

         
        public void setCreatedById(java.lang.String param) {
            localCreatedByIdTracker = param != null;

            this.localCreatedById = param;
        }

        public boolean isDataSourceSpecified() {
            return localDataSourceTracker;
        }

         
        public java.lang.String getDataSource() {
            return localDataSource;
        }

         
        public void setDataSource(java.lang.String param) {
            localDataSourceTracker = param != null;

            this.localDataSource = param;
        }

        public boolean isDistrictCodeSpecified() {
            return localDistrictCodeTracker;
        }

         
        public java.lang.String getDistrictCode() {
            return localDistrictCode;
        }

         
        public void setDistrictCode(java.lang.String param) {
            localDistrictCodeTracker = param != null;

            this.localDistrictCode = param;
        }

        public boolean isDomainModelSpecified() {
            return localDomainModelTracker;
        }

         
        public java.lang.String getDomainModel() {
            return localDomainModel;
        }

         
        public void setDomainModel(java.lang.String param) {
            localDomainModelTracker = param != null;

            this.localDomainModel = param;
        }

        public boolean isDomainTypeSpecified() {
            return localDomainTypeTracker;
        }

         
        public java.lang.String getDomainType() {
            return localDomainType;
        }

         
        public void setDomainType(java.lang.String param) {
            localDomainTypeTracker = param != null;

            this.localDomainType = param;
        }

        public boolean isEstimatedTimeBidSpecified() {
            return localEstimatedTimeBidTracker;
        }

         
        public java.util.Calendar getEstimatedTimeBid() {
            return localEstimatedTimeBid;
        }

         
        public void setEstimatedTimeBid(java.util.Calendar param) {
            localEstimatedTimeBidTracker = param != null;

            this.localEstimatedTimeBid = param;
        }

        public boolean isFirstCompanyIdSpecified() {
            return localFirstCompanyIdTracker;
        }

         
        public java.lang.String getFirstCompanyId() {
            return localFirstCompanyId;
        }

         
        public void setFirstCompanyId(java.lang.String param) {
            localFirstCompanyIdTracker = param != null;

            this.localFirstCompanyId = param;
        }

        public boolean isFirstCompanyNameSpecified() {
            return localFirstCompanyNameTracker;
        }

         
        public java.lang.String getFirstCompanyName() {
            return localFirstCompanyName;
        }

         
        public void setFirstCompanyName(java.lang.String param) {
            localFirstCompanyNameTracker = param != null;

            this.localFirstCompanyName = param;
        }

        public boolean isFirstCompanyOrgCodeSpecified() {
            return localFirstCompanyOrgCodeTracker;
        }

         
        public java.lang.String getFirstCompanyOrgCode() {
            return localFirstCompanyOrgCode;
        }

         
        public void setFirstCompanyOrgCode(java.lang.String param) {
            localFirstCompanyOrgCodeTracker = param != null;

            this.localFirstCompanyOrgCode = param;
        }

        public boolean isFrequencyConversionSpecified() {
            return localFrequencyConversionTracker;
        }

         
        public boolean getFrequencyConversion() {
            return localFrequencyConversion;
        }

         
        public void setFrequencyConversion(boolean param) {
            // setting primitive attribute tracker to true
            localFrequencyConversionTracker = true;

            this.localFrequencyConversion = param;
        }

        public boolean isFunnelStageSpecified() {
            return localFunnelStageTracker;
        }

         
        public java.lang.String getFunnelStage() {
            return localFunnelStage;
        }

         
        public void setFunnelStage(java.lang.String param) {
            localFunnelStageTracker = param != null;

            this.localFunnelStage = param;
        }

        public boolean isFunnelStageUpdateTimeSpecified() {
            return localFunnelStageUpdateTimeTracker;
        }

         
        public java.util.Calendar getFunnelStageUpdateTime() {
            return localFunnelStageUpdateTime;
        }

         
        public void setFunnelStageUpdateTime(java.util.Calendar param) {
            localFunnelStageUpdateTimeTracker = param != null;

            this.localFunnelStageUpdateTime = param;
        }

        public boolean isGridCenterCodeSpecified() {
            return localGridCenterCodeTracker;
        }

         
        public java.lang.String getGridCenterCode() {
            return localGridCenterCode;
        }

         
        public void setGridCenterCode(java.lang.String param) {
            localGridCenterCodeTracker = param != null;

            this.localGridCenterCode = param;
        }

        public boolean isGridCenterNameSpecified() {
            return localGridCenterNameTracker;
        }

         
        public java.lang.String getGridCenterName() {
            return localGridCenterName;
        }

         
        public void setGridCenterName(java.lang.String param) {
            localGridCenterNameTracker = param != null;

            this.localGridCenterName = param;
        }

        public boolean isGridCodeSpecified() {
            return localGridCodeTracker;
        }

         
        public java.lang.String getGridCode() {
            return localGridCode;
        }

         
        public void setGridCode(java.lang.String param) {
            localGridCodeTracker = param != null;

            this.localGridCode = param;
        }

        public boolean isGridNameSpecified() {
            return localGridNameTracker;
        }

         
        public java.lang.String getGridName() {
            return localGridName;
        }

         
        public void setGridName(java.lang.String param) {
            localGridNameTracker = param != null;

            this.localGridName = param;
        }

        public boolean isGridUserCodeSpecified() {
            return localGridUserCodeTracker;
        }

         
        public java.lang.String getGridUserCode() {
            return localGridUserCode;
        }

         
        public void setGridUserCode(java.lang.String param) {
            localGridUserCodeTracker = param != null;

            this.localGridUserCode = param;
        }

        public boolean isGridUserNameSpecified() {
            return localGridUserNameTracker;
        }

         
        public java.lang.String getGridUserName() {
            return localGridUserName;
        }

         
        public void setGridUserName(java.lang.String param) {
            localGridUserNameTracker = param != null;

            this.localGridUserName = param;
        }

        public boolean isIdSpecified() {
            return localIdTracker;
        }

         
        public java.lang.String getId() {
            return localId;
        }

         
        public void setId(java.lang.String param) {
            localIdTracker = param != null;

            this.localId = param;
        }

        public boolean isLastModifiedByIdSpecified() {
            return localLastModifiedByIdTracker;
        }

         
        public java.lang.String getLastModifiedById() {
            return localLastModifiedById;
        }

         
        public void setLastModifiedById(java.lang.String param) {
            localLastModifiedByIdTracker = param != null;

            this.localLastModifiedById = param;
        }

        public boolean isLesFlagSpecified() {
            return localLesFlagTracker;
        }

         
        public java.lang.String getLesFlag() {
            return localLesFlag;
        }

         
        public void setLesFlag(java.lang.String param) {
            localLesFlagTracker = param != null;

            this.localLesFlag = param;
        }

        public boolean isLesMsgSpecified() {
            return localLesMsgTracker;
        }

         
        public java.lang.String getLesMsg() {
            return localLesMsg;
        }

         
        public void setLesMsg(java.lang.String param) {
            localLesMsgTracker = param != null;

            this.localLesMsg = param;
        }

        public boolean isLoginLevelSpecified() {
            return localLoginLevelTracker;
        }

         
        public int getLoginLevel() {
            return localLoginLevel;
        }

         
        public void setLoginLevel(int param) {
            // setting primitive attribute tracker to true
            localLoginLevelTracker = param != java.lang.Integer.MIN_VALUE;

            this.localLoginLevel = param;
        }

        public boolean isMsgIdSpecified() {
            return localMsgIdTracker;
        }

         
        public int getMsgId() {
            return localMsgId;
        }

         
        public void setMsgId(int param) {
            // setting primitive attribute tracker to true
            localMsgIdTracker = param != java.lang.Integer.MIN_VALUE;

            this.localMsgId = param;
        }

        public boolean isProjectCodeSpecified() {
            return localProjectCodeTracker;
        }

         
        public java.lang.String getProjectCode() {
            return localProjectCode;
        }

         
        public void setProjectCode(java.lang.String param) {
            localProjectCodeTracker = param != null;

            this.localProjectCode = param;
        }

        public boolean isProjectCreaterCodeSpecified() {
            return localProjectCreaterCodeTracker;
        }

         
        public java.lang.String getProjectCreaterCode() {
            return localProjectCreaterCode;
        }

         
        public void setProjectCreaterCode(java.lang.String param) {
            localProjectCreaterCodeTracker = param != null;

            this.localProjectCreaterCode = param;
        }

        public boolean isProjectCreaterIdSpecified() {
            return localProjectCreaterIdTracker;
        }

         
        public java.lang.String getProjectCreaterId() {
            return localProjectCreaterId;
        }

         
        public void setProjectCreaterId(java.lang.String param) {
            localProjectCreaterIdTracker = param != null;

            this.localProjectCreaterId = param;
        }

        public boolean isProjectCreaterNameSpecified() {
            return localProjectCreaterNameTracker;
        }

         
        public java.lang.String getProjectCreaterName() {
            return localProjectCreaterName;
        }

         
        public void setProjectCreaterName(java.lang.String param) {
            localProjectCreaterNameTracker = param != null;

            this.localProjectCreaterName = param;
        }

        public boolean isProjectFinishDateSpecified() {
            return localProjectFinishDateTracker;
        }

         
        public java.util.Calendar getProjectFinishDate() {
            return localProjectFinishDate;
        }

         
        public void setProjectFinishDate(java.util.Calendar param) {
            localProjectFinishDateTracker = param != null;

            this.localProjectFinishDate = param;
        }

        public boolean isProjectLastModifiedDateSpecified() {
            return localProjectLastModifiedDateTracker;
        }

         
        public java.util.Calendar getProjectLastModifiedDate() {
            return localProjectLastModifiedDate;
        }

         
        public void setProjectLastModifiedDate(java.util.Calendar param) {
            localProjectLastModifiedDateTracker = param != null;

            this.localProjectLastModifiedDate = param;
        }

        public boolean isProjectManagerCenterSpecified() {
            return localProjectManagerCenterTracker;
        }

         
        public java.lang.String getProjectManagerCenter() {
            return localProjectManagerCenter;
        }

         
        public void setProjectManagerCenter(java.lang.String param) {
            localProjectManagerCenterTracker = param != null;

            this.localProjectManagerCenter = param;
        }

        public boolean isProjectManagerCodeSpecified() {
            return localProjectManagerCodeTracker;
        }

         
        public java.lang.String getProjectManagerCode() {
            return localProjectManagerCode;
        }

         
        public void setProjectManagerCode(java.lang.String param) {
            localProjectManagerCodeTracker = param != null;

            this.localProjectManagerCode = param;
        }

        public boolean isProjectManagerIdSpecified() {
            return localProjectManagerIdTracker;
        }

         
        public java.lang.String getProjectManagerId() {
            return localProjectManagerId;
        }

         
        public void setProjectManagerId(java.lang.String param) {
            localProjectManagerIdTracker = param != null;

            this.localProjectManagerId = param;
        }

        public boolean isProjectManagerNameSpecified() {
            return localProjectManagerNameTracker;
        }

         
        public java.lang.String getProjectManagerName() {
            return localProjectManagerName;
        }

         
        public void setProjectManagerName(java.lang.String param) {
            localProjectManagerNameTracker = param != null;

            this.localProjectManagerName = param;
        }

        public boolean isProjectNameSpecified() {
            return localProjectNameTracker;
        }

         
        public java.lang.String getProjectName() {
            return localProjectName;
        }

         
        public void setProjectName(java.lang.String param) {
            localProjectNameTracker = param != null;

            this.localProjectName = param;
        }

        public boolean isProjectSourceSpecified() {
            return localProjectSourceTracker;
        }

         
        public java.lang.String getProjectSource() {
            return localProjectSource;
        }

         
        public void setProjectSource(java.lang.String param) {
            localProjectSourceTracker = param != null;

            this.localProjectSource = param;
        }

        public boolean isProjectStatusSpecified() {
            return localProjectStatusTracker;
        }

         
        public java.lang.String getProjectStatus() {
            return localProjectStatus;
        }

         
        public void setProjectStatus(java.lang.String param) {
            localProjectStatusTracker = param != null;

            this.localProjectStatus = param;
        }

        public boolean isProjectTypeSpecified() {
            return localProjectTypeTracker;
        }

         
        public java.lang.String getProjectType() {
            return localProjectType;
        }

         
        public void setProjectType(java.lang.String param) {
            localProjectTypeTracker = param != null;

            this.localProjectType = param;
        }

        public boolean isPurchaseForecastListSpecified() {
            return localPurchaseForecastListTracker;
        }

         
        public ProjectPurchaseForecastSaveParam[] getPurchaseForecastList() {
            return localPurchaseForecastList;
        }

         
        protected void validatePurchaseForecastList(
            ProjectPurchaseForecastSaveParam[] param) {
        }

         
        public void setPurchaseForecastList(
            ProjectPurchaseForecastSaveParam[] param) {
            validatePurchaseForecastList(param);

            localPurchaseForecastListTracker = true;

            this.localPurchaseForecastList = param;
        }

         
        public void addPurchaseForecastList(
            ProjectPurchaseForecastSaveParam param) {
            if (localPurchaseForecastList == null) {
                localPurchaseForecastList = new ProjectPurchaseForecastSaveParam[] {
                        
                    };
            }

            //update the setting tracker
            localPurchaseForecastListTracker = true;

            java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localPurchaseForecastList);
            list.add(param);
            this.localPurchaseForecastList = (ProjectPurchaseForecastSaveParam[]) list.toArray(new ProjectPurchaseForecastSaveParam[list.size()]);
        }

        public boolean isSalesModelSpecified() {
            return localSalesModelTracker;
        }

         
        public java.lang.String getSalesModel() {
            return localSalesModel;
        }

         
        public void setSalesModel(java.lang.String param) {
            localSalesModelTracker = param != null;

            this.localSalesModel = param;
        }

        public boolean isSamplePlateTypeSpecified() {
            return localSamplePlateTypeTracker;
        }

         
        public java.lang.String getSamplePlateType() {
            return localSamplePlateType;
        }

         
        public void setSamplePlateType(java.lang.String param) {
            localSamplePlateTypeTracker = param != null;

            this.localSamplePlateType = param;
        }

        public boolean isSendGpmsResultSpecified() {
            return localSendGpmsResultTracker;
        }

         
        public java.lang.String getSendGpmsResult() {
            return localSendGpmsResult;
        }

         
        public void setSendGpmsResult(java.lang.String param) {
            localSendGpmsResultTracker = param != null;

            this.localSendGpmsResult = param;
        }

        public boolean isSendGpmsTimeSpecified() {
            return localSendGpmsTimeTracker;
        }

         
        public java.util.Calendar getSendGpmsTime() {
            return localSendGpmsTime;
        }

         
        public void setSendGpmsTime(java.util.Calendar param) {
            localSendGpmsTimeTracker = param != null;

            this.localSendGpmsTime = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://project.funnel.api.manage.hps.com/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":projectROneSaveParam", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "projectROneSaveParam", xmlWriter);
            }

            if (localAddressCityTracker) {
                namespace = "";
                writeStartElement(null, namespace, "addressCity", xmlWriter);

                if (localAddressCity == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "addressCity cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAddressCity);
                }

                xmlWriter.writeEndElement();
            }

            if (localAddressCountyTracker) {
                namespace = "";
                writeStartElement(null, namespace, "addressCounty", xmlWriter);

                if (localAddressCounty == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "addressCounty cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAddressCounty);
                }

                xmlWriter.writeEndElement();
            }

            if (localAddressDetailTracker) {
                namespace = "";
                writeStartElement(null, namespace, "addressDetail", xmlWriter);

                if (localAddressDetail == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "addressDetail cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAddressDetail);
                }

                xmlWriter.writeEndElement();
            }

            if (localAddressProvinceTracker) {
                namespace = "";
                writeStartElement(null, namespace, "addressProvince", xmlWriter);

                if (localAddressProvince == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "addressProvince cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAddressProvince);
                }

                xmlWriter.writeEndElement();
            }

            if (localAreaTracker) {
                namespace = "";
                writeStartElement(null, namespace, "area", xmlWriter);

                if (localArea == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "area cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localArea);
                }

                xmlWriter.writeEndElement();
            }

            if (localBeAfterPurchaseTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beAfterPurchase", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beAfterPurchase cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeAfterPurchase));
                }

                xmlWriter.writeEndElement();
            }

            if (localBeGroupBuyTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beGroupBuy", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beGroupBuy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeGroupBuy));
                }

                xmlWriter.writeEndElement();
            }

            if (localBeImgageProjectTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beImgageProject", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beImgageProject cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeImgageProject));
                }

                xmlWriter.writeEndElement();
            }

            if (localBeManualSelectProjectFinishTracker) {
                namespace = "";
                writeStartElement(null, namespace,
                    "beManualSelectProjectFinish", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beManualSelectProjectFinish cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeManualSelectProjectFinish));
                }

                xmlWriter.writeEndElement();
            }

            if (localBeSendGpmsTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beSendGpms", xmlWriter);

                if (localBeSendGpms == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "beSendGpms cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localBeSendGpms);
                }

                xmlWriter.writeEndElement();
            }

            if (localBeStrategyTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beStrategy", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beStrategy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeStrategy));
                }

                xmlWriter.writeEndElement();
            }

            if (localBeTurnStraightGuestTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beTurnStraightGuest",
                    xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beTurnStraightGuest cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeTurnStraightGuest));
                }

                xmlWriter.writeEndElement();
            }

            if (localCancelPersonCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "cancelPersonCode", xmlWriter);

                if (localCancelPersonCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "cancelPersonCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCancelPersonCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localCancelReasonTracker) {
                namespace = "";
                writeStartElement(null, namespace, "cancelReason", xmlWriter);

                if (localCancelReason == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "cancelReason cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCancelReason);
                }

                xmlWriter.writeEndElement();
            }

            if (localCancelTimeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "cancelTime", xmlWriter);

                if (localCancelTime == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "cancelTime cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localCancelTime));
                }

                xmlWriter.writeEndElement();
            }

            if (localCenterTracker) {
                namespace = "";
                writeStartElement(null, namespace, "center", xmlWriter);

                if (localCenter == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "center cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCenter);
                }

                xmlWriter.writeEndElement();
            }

            if (localCleanEnergyTracker) {
                namespace = "";
                writeStartElement(null, namespace, "cleanEnergy", xmlWriter);

                if (localCleanEnergy == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "cleanEnergy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCleanEnergy);
                }

                xmlWriter.writeEndElement();
            }

            if (localCleanEnergyTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "cleanEnergyType", xmlWriter);

                if (localCleanEnergyType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "cleanEnergyType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCleanEnergyType);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdById", xmlWriter);

                if (localCreatedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localDataSourceTracker) {
                namespace = "";
                writeStartElement(null, namespace, "dataSource", xmlWriter);

                if (localDataSource == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "dataSource cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDataSource);
                }

                xmlWriter.writeEndElement();
            }

            if (localDistrictCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "districtCode", xmlWriter);

                if (localDistrictCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "districtCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDistrictCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localDomainModelTracker) {
                namespace = "";
                writeStartElement(null, namespace, "domainModel", xmlWriter);

                if (localDomainModel == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "domainModel cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDomainModel);
                }

                xmlWriter.writeEndElement();
            }

            if (localDomainTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "domainType", xmlWriter);

                if (localDomainType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "domainType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDomainType);
                }

                xmlWriter.writeEndElement();
            }

            if (localEstimatedTimeBidTracker) {
                namespace = "";
                writeStartElement(null, namespace, "estimatedTimeBid", xmlWriter);

                if (localEstimatedTimeBid == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "estimatedTimeBid cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localEstimatedTimeBid));
                }

                xmlWriter.writeEndElement();
            }

            if (localFirstCompanyIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "firstCompanyId", xmlWriter);

                if (localFirstCompanyId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "firstCompanyId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localFirstCompanyId);
                }

                xmlWriter.writeEndElement();
            }

            if (localFirstCompanyNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "firstCompanyName", xmlWriter);

                if (localFirstCompanyName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "firstCompanyName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localFirstCompanyName);
                }

                xmlWriter.writeEndElement();
            }

            if (localFirstCompanyOrgCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "firstCompanyOrgCode",
                    xmlWriter);

                if (localFirstCompanyOrgCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "firstCompanyOrgCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localFirstCompanyOrgCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localFrequencyConversionTracker) {
                namespace = "";
                writeStartElement(null, namespace, "frequencyConversion",
                    xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "frequencyConversion cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localFrequencyConversion));
                }

                xmlWriter.writeEndElement();
            }

            if (localFunnelStageTracker) {
                namespace = "";
                writeStartElement(null, namespace, "funnelStage", xmlWriter);

                if (localFunnelStage == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "funnelStage cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localFunnelStage);
                }

                xmlWriter.writeEndElement();
            }

            if (localFunnelStageUpdateTimeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "funnelStageUpdateTime",
                    xmlWriter);

                if (localFunnelStageUpdateTime == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "funnelStageUpdateTime cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localFunnelStageUpdateTime));
                }

                xmlWriter.writeEndElement();
            }

            if (localGridCenterCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "gridCenterCode", xmlWriter);

                if (localGridCenterCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "gridCenterCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGridCenterCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localGridCenterNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "gridCenterName", xmlWriter);

                if (localGridCenterName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "gridCenterName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGridCenterName);
                }

                xmlWriter.writeEndElement();
            }

            if (localGridCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "gridCode", xmlWriter);

                if (localGridCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "gridCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGridCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localGridNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "gridName", xmlWriter);

                if (localGridName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "gridName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGridName);
                }

                xmlWriter.writeEndElement();
            }

            if (localGridUserCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "gridUserCode", xmlWriter);

                if (localGridUserCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "gridUserCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGridUserCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localGridUserNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "gridUserName", xmlWriter);

                if (localGridUserName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "gridUserName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGridUserName);
                }

                xmlWriter.writeEndElement();
            }

            if (localIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "id", xmlWriter);

                if (localId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "id cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localId);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedById", xmlWriter);

                if (localLastModifiedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localLesFlagTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lesFlag", xmlWriter);

                if (localLesFlag == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lesFlag cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLesFlag);
                }

                xmlWriter.writeEndElement();
            }

            if (localLesMsgTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lesMsg", xmlWriter);

                if (localLesMsg == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lesMsg cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLesMsg);
                }

                xmlWriter.writeEndElement();
            }

            if (localLoginLevelTracker) {
                namespace = "";
                writeStartElement(null, namespace, "loginLevel", xmlWriter);

                if (localLoginLevel == java.lang.Integer.MIN_VALUE) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "loginLevel cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localLoginLevel));
                }

                xmlWriter.writeEndElement();
            }

            if (localMsgIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "msgId", xmlWriter);

                if (localMsgId == java.lang.Integer.MIN_VALUE) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "msgId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localMsgId));
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectCode", xmlWriter);

                if (localProjectCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectCreaterCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectCreaterCode",
                    xmlWriter);

                if (localProjectCreaterCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectCreaterCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectCreaterCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectCreaterIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectCreaterId", xmlWriter);

                if (localProjectCreaterId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectCreaterId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectCreaterId);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectCreaterNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectCreaterName",
                    xmlWriter);

                if (localProjectCreaterName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectCreaterName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectCreaterName);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectFinishDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectFinishDate",
                    xmlWriter);

                if (localProjectFinishDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectFinishDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localProjectFinishDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectLastModifiedDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectLastModifiedDate",
                    xmlWriter);

                if (localProjectLastModifiedDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectLastModifiedDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localProjectLastModifiedDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectManagerCenterTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectManagerCenter",
                    xmlWriter);

                if (localProjectManagerCenter == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectManagerCenter cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectManagerCenter);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectManagerCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectManagerCode",
                    xmlWriter);

                if (localProjectManagerCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectManagerCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectManagerCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectManagerIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectManagerId", xmlWriter);

                if (localProjectManagerId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectManagerId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectManagerId);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectManagerNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectManagerName",
                    xmlWriter);

                if (localProjectManagerName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectManagerName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectManagerName);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectName", xmlWriter);

                if (localProjectName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectName);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectSourceTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectSource", xmlWriter);

                if (localProjectSource == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectSource cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectSource);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectStatusTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectStatus", xmlWriter);

                if (localProjectStatus == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectStatus cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectStatus);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectType", xmlWriter);

                if (localProjectType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectType);
                }

                xmlWriter.writeEndElement();
            }

            if (localPurchaseForecastListTracker) {
                if (localPurchaseForecastList != null) {
                    for (int i = 0; i < localPurchaseForecastList.length;
                            i++) {
                        if (localPurchaseForecastList[i] != null) {
                            localPurchaseForecastList[i].serialize(new javax.xml.namespace.QName(
                                    "", "purchaseForecastList"), xmlWriter);
                        } else {
                            writeStartElement(null, "", "purchaseForecastList",
                                xmlWriter);

                            // write the nil attribute
                            writeAttribute("xsi",
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "nil", "1", xmlWriter);
                            xmlWriter.writeEndElement();
                        }
                    }
                } else {
                    writeStartElement(null, "", "purchaseForecastList",
                        xmlWriter);

                    // write the nil attribute
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "nil",
                        "1", xmlWriter);
                    xmlWriter.writeEndElement();
                }
            }

            if (localSalesModelTracker) {
                namespace = "";
                writeStartElement(null, namespace, "salesModel", xmlWriter);

                if (localSalesModel == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "salesModel cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localSalesModel);
                }

                xmlWriter.writeEndElement();
            }

            if (localSamplePlateTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "samplePlateType", xmlWriter);

                if (localSamplePlateType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "samplePlateType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localSamplePlateType);
                }

                xmlWriter.writeEndElement();
            }

            if (localSendGpmsResultTracker) {
                namespace = "";
                writeStartElement(null, namespace, "sendGpmsResult", xmlWriter);

                if (localSendGpmsResult == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "sendGpmsResult cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localSendGpmsResult);
                }

                xmlWriter.writeEndElement();
            }

            if (localSendGpmsTimeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "sendGpmsTime", xmlWriter);

                if (localSendGpmsTime == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "sendGpmsTime cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localSendGpmsTime));
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static ProjectROneSaveParam parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                ProjectROneSaveParam object = new ProjectROneSaveParam();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"projectROneSaveParam".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (ProjectROneSaveParam) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    java.util.ArrayList list57 = new java.util.ArrayList();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "addressCity").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "addressCity" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAddressCity(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "addressCounty").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "addressCounty" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAddressCounty(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "addressDetail").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "addressDetail" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAddressDetail(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "addressProvince").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "addressProvince" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAddressProvince(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "area").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "area" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setArea(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beAfterPurchase").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beAfterPurchase" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeAfterPurchase(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beGroupBuy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beGroupBuy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeGroupBuy(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beImgageProject").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beImgageProject" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeImgageProject(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "beManualSelectProjectFinish").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " +
                                "beManualSelectProjectFinish" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeManualSelectProjectFinish(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beSendGpms").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beSendGpms" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeSendGpms(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beStrategy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beStrategy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeStrategy(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "beTurnStraightGuest").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beTurnStraightGuest" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeTurnStraightGuest(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "cancelPersonCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "cancelPersonCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCancelPersonCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "cancelReason").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "cancelReason" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCancelReason(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "cancelTime").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "cancelTime" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCancelTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "center").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "center" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCenter(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "cleanEnergy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "cleanEnergy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCleanEnergy(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "cleanEnergyType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "cleanEnergyType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCleanEnergyType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "dataSource").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "dataSource" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDataSource(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "districtCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "districtCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDistrictCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "domainModel").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "domainModel" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDomainModel(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "domainType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "domainType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDomainType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "estimatedTimeBid").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "estimatedTimeBid" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setEstimatedTimeBid(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "firstCompanyId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "firstCompanyId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFirstCompanyId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "firstCompanyName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "firstCompanyName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFirstCompanyName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "firstCompanyOrgCode").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "firstCompanyOrgCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFirstCompanyOrgCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "frequencyConversion").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "frequencyConversion" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFrequencyConversion(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "funnelStage").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "funnelStage" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFunnelStage(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "funnelStageUpdateTime").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "funnelStageUpdateTime" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setFunnelStageUpdateTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "gridCenterCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "gridCenterCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGridCenterCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "gridCenterName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "gridCenterName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGridCenterName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "gridCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "gridCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGridCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "gridName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "gridName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGridName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "gridUserCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "gridUserCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGridUserCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "gridUserName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "gridUserName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGridUserName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "id").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "id" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lesFlag").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lesFlag" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLesFlag(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lesMsg").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lesMsg" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLesMsg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "loginLevel").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "loginLevel" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLoginLevel(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                        object.setLoginLevel(java.lang.Integer.MIN_VALUE);
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "msgId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "msgId" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setMsgId(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                        object.setMsgId(java.lang.Integer.MIN_VALUE);
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectCreaterCode").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectCreaterCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectCreaterCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectCreaterId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectCreaterId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectCreaterId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectCreaterName").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectCreaterName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectCreaterName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectFinishDate").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectFinishDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectFinishDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectLastModifiedDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectLastModifiedDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectLastModifiedDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectManagerCenter").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectManagerCenter" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectManagerCenter(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectManagerCode").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectManagerCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectManagerCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectManagerId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectManagerId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectManagerId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectManagerName").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectManagerName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectManagerName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectSource").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectSource" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectSource(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectStatus").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectStatus" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectStatus(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "purchaseForecastList").equals(reader.getName())) {
                        // Process the array and step past its final element's end.
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            list57.add(null);
                            reader.next();
                        } else {
                            list57.add(ProjectPurchaseForecastSaveParam.Factory.parse(
                                    reader));
                        }

                        //loop until we find a start element that is not part of this array
                        boolean loopDone57 = false;

                        while (!loopDone57) {
                            // We should be at the end element, but make sure
                            while (!reader.isEndElement())
                                reader.next();

                            // Step out of this element
                            reader.next();

                            // Step to next element event.
                            while (!reader.isStartElement() &&
                                    !reader.isEndElement())
                                reader.next();

                            if (reader.isEndElement()) {
                                //two continuous end elements means we are exiting the xml structure
                                loopDone57 = true;
                            } else {
                                if (new javax.xml.namespace.QName("",
                                            "purchaseForecastList").equals(
                                            reader.getName())) {
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                            "nil");

                                    if ("true".equals(nillableValue) ||
                                            "1".equals(nillableValue)) {
                                        list57.add(null);
                                        reader.next();
                                    } else {
                                        list57.add(ProjectPurchaseForecastSaveParam.Factory.parse(
                                                reader));
                                    }
                                } else {
                                    loopDone57 = true;
                                }
                            }
                        }

                        // call the converter utility  to convert and set the array
                        object.setPurchaseForecastList((ProjectPurchaseForecastSaveParam[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                ProjectPurchaseForecastSaveParam.class, list57));
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "salesModel").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "salesModel" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setSalesModel(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "samplePlateType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "samplePlateType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setSamplePlateType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "sendGpmsResult").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "sendGpmsResult" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setSendGpmsResult(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "sendGpmsTime").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "sendGpmsTime" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setSendGpmsTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class SaveProjectFromQYG implements org.apache.axis2.databinding.ADBBean {
         

         
        protected ProjectSaveParam[] localArg0;

         
        protected boolean localArg0Tracker = false;

        public boolean isArg0Specified() {
            return localArg0Tracker;
        }

         
        public ProjectSaveParam[] getArg0() {
            return localArg0;
        }

         
        protected void validateArg0(ProjectSaveParam[] param) {
        }

         
        public void setArg0(ProjectSaveParam[] param) {
            validateArg0(param);

            localArg0Tracker = param != null;

            this.localArg0 = param;
        }

         
        public void addArg0(ProjectSaveParam param) {
            if (localArg0 == null) {
                localArg0 = new ProjectSaveParam[] {  };
            }

            //update the setting tracker
            localArg0Tracker = true;

            java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localArg0);
            list.add(param);
            this.localArg0 = (ProjectSaveParam[]) list.toArray(new ProjectSaveParam[list.size()]);
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            if (serializeType) {
                java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                        "http://project.funnel.api.manage.hps.com/");

                if ((namespacePrefix != null) &&
                        (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        namespacePrefix + ":saveProjectFromQYG", xmlWriter);
                } else {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "saveProjectFromQYG", xmlWriter);
                }
            }

            if (localArg0Tracker) {
                if (localArg0 != null) {
                    for (int i = 0; i < localArg0.length; i++) {
                        if (localArg0[i] != null) {
                            localArg0[i].serialize(new javax.xml.namespace.QName(
                                    "", "arg0"), xmlWriter);
                        } else {
                            // we don't have to do any thing since minOccures is zero
                        }
                    }
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "arg0 cannot be null!!");
                }
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static SaveProjectFromQYG parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                SaveProjectFromQYG object = new SaveProjectFromQYG();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"saveProjectFromQYG".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (SaveProjectFromQYG) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    java.util.ArrayList list1 = new java.util.ArrayList();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "arg0").equals(
                                reader.getName())) {
                        // Process the array and step past its final element's end.
                        list1.add(ProjectSaveParam.Factory.parse(reader));

                        //loop until we find a start element that is not part of this array
                        boolean loopDone1 = false;

                        while (!loopDone1) {
                            // We should be at the end element, but make sure
                            while (!reader.isEndElement())
                                reader.next();

                            // Step out of this element
                            reader.next();

                            // Step to next element event.
                            while (!reader.isStartElement() &&
                                    !reader.isEndElement())
                                reader.next();

                            if (reader.isEndElement()) {
                                //two continuous end elements means we are exiting the xml structure
                                loopDone1 = true;
                            } else {
                                if (new javax.xml.namespace.QName("", "arg0").equals(
                                            reader.getName())) {
                                    list1.add(ProjectSaveParam.Factory.parse(
                                            reader));
                                } else {
                                    loopDone1 = true;
                                }
                            }
                        }

                        // call the converter utility  to convert and set the array
                        object.setArg0((ProjectSaveParam[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                ProjectSaveParam.class, list1));
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class QueryProjectManagerFromHPSE implements org.apache.axis2.databinding.ADBBean {
        public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://project.funnel.api.manage.hps.com/",
                "queryProjectManagerFromHPS", "ns1");

         
        protected QueryProjectManagerFromHPS localQueryProjectManagerFromHPS;

         
        public QueryProjectManagerFromHPS getQueryProjectManagerFromHPS() {
            return localQueryProjectManagerFromHPS;
        }

         
        public void setQueryProjectManagerFromHPS(
            QueryProjectManagerFromHPS param) {
            this.localQueryProjectManagerFromHPS = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, MY_QNAME));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            //We can safely assume an element has only one type associated with it
            if (localQueryProjectManagerFromHPS == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "queryProjectManagerFromHPS cannot be null!");
            }

            localQueryProjectManagerFromHPS.serialize(MY_QNAME, xmlWriter);
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static QueryProjectManagerFromHPSE parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                QueryProjectManagerFromHPSE object = new QueryProjectManagerFromHPSE();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    while (!reader.isEndElement()) {
                        if (reader.isStartElement()) {
                            if (reader.isStartElement() &&
                                    new javax.xml.namespace.QName(
                                        "http://project.funnel.api.manage.hps.com/",
                                        "queryProjectManagerFromHPS").equals(
                                        reader.getName())) {
                                object.setQueryProjectManagerFromHPS(QueryProjectManagerFromHPS.Factory.parse(
                                        reader));
                            } // End of if for expected property start element

                            else {
                                // 3 - A start element we are not expecting indicates an invalid parameter was passed
                                throw new org.apache.axis2.databinding.ADBException(
                                    "Unexpected subelement " +
                                    reader.getName());
                            }
                        } else {
                            reader.next();
                        }
                    } // end of while loop
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class ProjectSaveParam extends ToString implements org.apache.axis2.databinding.ADBBean {
         

         
        protected ProjectROneSaveParam localProjectROneSaveParam;

         
        protected boolean localProjectROneSaveParamTracker = false;

         
        protected ProjectRThreeSaveParam localProjectRThreeSaveParam;

         
        protected boolean localProjectRThreeSaveParamTracker = false;

         
        protected ProjectRTwoSaveParam localProjectRTwoSaveParam;

         
        protected boolean localProjectRTwoSaveParamTracker = false;

        public boolean isProjectROneSaveParamSpecified() {
            return localProjectROneSaveParamTracker;
        }

         
        public ProjectROneSaveParam getProjectROneSaveParam() {
            return localProjectROneSaveParam;
        }

         
        public void setProjectROneSaveParam(ProjectROneSaveParam param) {
            localProjectROneSaveParamTracker = param != null;

            this.localProjectROneSaveParam = param;
        }

        public boolean isProjectRThreeSaveParamSpecified() {
            return localProjectRThreeSaveParamTracker;
        }

         
        public ProjectRThreeSaveParam getProjectRThreeSaveParam() {
            return localProjectRThreeSaveParam;
        }

         
        public void setProjectRThreeSaveParam(ProjectRThreeSaveParam param) {
            localProjectRThreeSaveParamTracker = param != null;

            this.localProjectRThreeSaveParam = param;
        }

        public boolean isProjectRTwoSaveParamSpecified() {
            return localProjectRTwoSaveParamTracker;
        }

         
        public ProjectRTwoSaveParam getProjectRTwoSaveParam() {
            return localProjectRTwoSaveParam;
        }

         
        public void setProjectRTwoSaveParam(ProjectRTwoSaveParam param) {
            localProjectRTwoSaveParamTracker = param != null;

            this.localProjectRTwoSaveParam = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://project.funnel.api.manage.hps.com/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":projectSaveParam", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "projectSaveParam", xmlWriter);
            }

            if (localProjectROneSaveParamTracker) {
                if (localProjectROneSaveParam == null) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectROneSaveParam cannot be null!!");
                }

                localProjectROneSaveParam.serialize(new javax.xml.namespace.QName(
                        "", "projectROneSaveParam"), xmlWriter);
            }

            if (localProjectRThreeSaveParamTracker) {
                if (localProjectRThreeSaveParam == null) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectRThreeSaveParam cannot be null!!");
                }

                localProjectRThreeSaveParam.serialize(new javax.xml.namespace.QName(
                        "", "projectRThreeSaveParam"), xmlWriter);
            }

            if (localProjectRTwoSaveParamTracker) {
                if (localProjectRTwoSaveParam == null) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectRTwoSaveParam cannot be null!!");
                }

                localProjectRTwoSaveParam.serialize(new javax.xml.namespace.QName(
                        "", "projectRTwoSaveParam"), xmlWriter);
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static ProjectSaveParam parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                ProjectSaveParam object = new ProjectSaveParam();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"projectSaveParam".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (ProjectSaveParam) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectROneSaveParam").equals(reader.getName())) {
                        object.setProjectROneSaveParam(ProjectROneSaveParam.Factory.parse(
                                reader));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectRThreeSaveParam").equals(
                                reader.getName())) {
                        object.setProjectRThreeSaveParam(ProjectRThreeSaveParam.Factory.parse(
                                reader));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "projectRTwoSaveParam").equals(reader.getName())) {
                        object.setProjectRTwoSaveParam(ProjectRTwoSaveParam.Factory.parse(
                                reader));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class ProjectPurchaseForecastSaveParam extends ToString
        implements org.apache.axis2.databinding.ADBBean {
         

         
        protected boolean localBeWisdom;

         
        protected boolean localBeWisdomTracker = false;

         
        protected java.lang.String localBrand;

         
        protected boolean localBrandTracker = false;

         
        protected java.lang.String localCreatedById;

         
        protected boolean localCreatedByIdTracker = false;

         
        protected long localEstimatedQuantity;

         
        protected boolean localEstimatedQuantityTracker = false;

         
        protected java.lang.String localId;

         
        protected boolean localIdTracker = false;

         
        protected java.lang.String localIndustryLine;

         
        protected boolean localIndustryLineTracker = false;

         
        protected java.lang.String localLastModifiedById;

         
        protected boolean localLastModifiedByIdTracker = false;

         
        protected java.lang.String localProductGroup;

         
        protected boolean localProductGroupTracker = false;

         
        protected java.lang.String localProjectId;

         
        protected boolean localProjectIdTracker = false;

         
        protected java.math.BigDecimal localPurchaseBudget;

         
        protected boolean localPurchaseBudgetTracker = false;

        public boolean isBeWisdomSpecified() {
            return localBeWisdomTracker;
        }

         
        public boolean getBeWisdom() {
            return localBeWisdom;
        }

         
        public void setBeWisdom(boolean param) {
            // setting primitive attribute tracker to true
            localBeWisdomTracker = true;

            this.localBeWisdom = param;
        }

        public boolean isBrandSpecified() {
            return localBrandTracker;
        }

         
        public java.lang.String getBrand() {
            return localBrand;
        }

         
        public void setBrand(java.lang.String param) {
            localBrandTracker = param != null;

            this.localBrand = param;
        }

        public boolean isCreatedByIdSpecified() {
            return localCreatedByIdTracker;
        }

         
        public java.lang.String getCreatedById() {
            return localCreatedById;
        }

         
        public void setCreatedById(java.lang.String param) {
            localCreatedByIdTracker = param != null;

            this.localCreatedById = param;
        }

        public boolean isEstimatedQuantitySpecified() {
            return localEstimatedQuantityTracker;
        }

         
        public long getEstimatedQuantity() {
            return localEstimatedQuantity;
        }

         
        public void setEstimatedQuantity(long param) {
            // setting primitive attribute tracker to true
            localEstimatedQuantityTracker = param != java.lang.Long.MIN_VALUE;

            this.localEstimatedQuantity = param;
        }

        public boolean isIdSpecified() {
            return localIdTracker;
        }

         
        public java.lang.String getId() {
            return localId;
        }

         
        public void setId(java.lang.String param) {
            localIdTracker = param != null;

            this.localId = param;
        }

        public boolean isIndustryLineSpecified() {
            return localIndustryLineTracker;
        }

         
        public java.lang.String getIndustryLine() {
            return localIndustryLine;
        }

         
        public void setIndustryLine(java.lang.String param) {
            localIndustryLineTracker = param != null;

            this.localIndustryLine = param;
        }

        public boolean isLastModifiedByIdSpecified() {
            return localLastModifiedByIdTracker;
        }

         
        public java.lang.String getLastModifiedById() {
            return localLastModifiedById;
        }

         
        public void setLastModifiedById(java.lang.String param) {
            localLastModifiedByIdTracker = param != null;

            this.localLastModifiedById = param;
        }

        public boolean isProductGroupSpecified() {
            return localProductGroupTracker;
        }

         
        public java.lang.String getProductGroup() {
            return localProductGroup;
        }

         
        public void setProductGroup(java.lang.String param) {
            localProductGroupTracker = param != null;

            this.localProductGroup = param;
        }

        public boolean isProjectIdSpecified() {
            return localProjectIdTracker;
        }

         
        public java.lang.String getProjectId() {
            return localProjectId;
        }

         
        public void setProjectId(java.lang.String param) {
            localProjectIdTracker = param != null;

            this.localProjectId = param;
        }

        public boolean isPurchaseBudgetSpecified() {
            return localPurchaseBudgetTracker;
        }

         
        public java.math.BigDecimal getPurchaseBudget() {
            return localPurchaseBudget;
        }

         
        public void setPurchaseBudget(java.math.BigDecimal param) {
            localPurchaseBudgetTracker = param != null;

            this.localPurchaseBudget = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://project.funnel.api.manage.hps.com/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":projectPurchaseForecastSaveParam",
                    xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "projectPurchaseForecastSaveParam", xmlWriter);
            }

            if (localBeWisdomTracker) {
                namespace = "";
                writeStartElement(null, namespace, "beWisdom", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "beWisdom cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBeWisdom));
                }

                xmlWriter.writeEndElement();
            }

            if (localBrandTracker) {
                namespace = "";
                writeStartElement(null, namespace, "brand", xmlWriter);

                if (localBrand == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "brand cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localBrand);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdById", xmlWriter);

                if (localCreatedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localEstimatedQuantityTracker) {
                namespace = "";
                writeStartElement(null, namespace, "estimatedQuantity",
                    xmlWriter);

                if (localEstimatedQuantity == java.lang.Long.MIN_VALUE) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "estimatedQuantity cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localEstimatedQuantity));
                }

                xmlWriter.writeEndElement();
            }

            if (localIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "id", xmlWriter);

                if (localId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "id cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localId);
                }

                xmlWriter.writeEndElement();
            }

            if (localIndustryLineTracker) {
                namespace = "";
                writeStartElement(null, namespace, "industryLine", xmlWriter);

                if (localIndustryLine == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "industryLine cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localIndustryLine);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedById", xmlWriter);

                if (localLastModifiedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localProductGroupTracker) {
                namespace = "";
                writeStartElement(null, namespace, "productGroup", xmlWriter);

                if (localProductGroup == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "productGroup cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProductGroup);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectId", xmlWriter);

                if (localProjectId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectId);
                }

                xmlWriter.writeEndElement();
            }

            if (localPurchaseBudgetTracker) {
                namespace = "";
                writeStartElement(null, namespace, "purchaseBudget", xmlWriter);

                if (localPurchaseBudget == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "purchaseBudget cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localPurchaseBudget));
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static ProjectPurchaseForecastSaveParam parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                ProjectPurchaseForecastSaveParam object = new ProjectPurchaseForecastSaveParam();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"projectPurchaseForecastSaveParam".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (ProjectPurchaseForecastSaveParam) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "beWisdom").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "beWisdom" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBeWisdom(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "brand").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "brand" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBrand(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "estimatedQuantity").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "estimatedQuantity" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setEstimatedQuantity(org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                        object.setEstimatedQuantity(java.lang.Long.MIN_VALUE);
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "id").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "id" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "industryLine").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "industryLine" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setIndustryLine(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "productGroup").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "productGroup" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProductGroup(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "purchaseBudget").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "purchaseBudget" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setPurchaseBudget(org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class BasicDto extends ToString implements org.apache.axis2.databinding.ADBBean {
         

         
        protected java.util.Calendar localBatchDate;

         
        protected boolean localBatchDateTracker = false;

         
        protected java.lang.String localCreProId;

         
        protected boolean localCreProIdTracker = false;

         
        protected java.lang.String localCreatedBy;

         
        protected boolean localCreatedByTracker = false;

         
        protected java.util.Calendar localCreatedDate;

         
        protected boolean localCreatedDateTracker = false;

         
        protected boolean localDeleted;

         
        protected boolean localDeletedTracker = false;

         
        protected java.lang.String localLastModifiedBy;

         
        protected boolean localLastModifiedByTracker = false;

         
        protected java.util.Calendar localLastModifiedDate;

         
        protected boolean localLastModifiedDateTracker = false;

         
        protected java.lang.String localModProId;

         
        protected boolean localModProIdTracker = false;

        public boolean isBatchDateSpecified() {
            return localBatchDateTracker;
        }

         
        public java.util.Calendar getBatchDate() {
            return localBatchDate;
        }

         
        public void setBatchDate(java.util.Calendar param) {
            localBatchDateTracker = param != null;

            this.localBatchDate = param;
        }

        public boolean isCreProIdSpecified() {
            return localCreProIdTracker;
        }

         
        public java.lang.String getCreProId() {
            return localCreProId;
        }

         
        public void setCreProId(java.lang.String param) {
            localCreProIdTracker = param != null;

            this.localCreProId = param;
        }

        public boolean isCreatedBySpecified() {
            return localCreatedByTracker;
        }

         
        public java.lang.String getCreatedBy() {
            return localCreatedBy;
        }

         
        public void setCreatedBy(java.lang.String param) {
            localCreatedByTracker = param != null;

            this.localCreatedBy = param;
        }

        public boolean isCreatedDateSpecified() {
            return localCreatedDateTracker;
        }

         
        public java.util.Calendar getCreatedDate() {
            return localCreatedDate;
        }

         
        public void setCreatedDate(java.util.Calendar param) {
            localCreatedDateTracker = param != null;

            this.localCreatedDate = param;
        }

        public boolean isDeletedSpecified() {
            return localDeletedTracker;
        }

         
        public boolean getDeleted() {
            return localDeleted;
        }

         
        public void setDeleted(boolean param) {
            // setting primitive attribute tracker to true
            localDeletedTracker = true;

            this.localDeleted = param;
        }

        public boolean isLastModifiedBySpecified() {
            return localLastModifiedByTracker;
        }

         
        public java.lang.String getLastModifiedBy() {
            return localLastModifiedBy;
        }

         
        public void setLastModifiedBy(java.lang.String param) {
            localLastModifiedByTracker = param != null;

            this.localLastModifiedBy = param;
        }

        public boolean isLastModifiedDateSpecified() {
            return localLastModifiedDateTracker;
        }

         
        public java.util.Calendar getLastModifiedDate() {
            return localLastModifiedDate;
        }

         
        public void setLastModifiedDate(java.util.Calendar param) {
            localLastModifiedDateTracker = param != null;

            this.localLastModifiedDate = param;
        }

        public boolean isModProIdSpecified() {
            return localModProIdTracker;
        }

         
        public java.lang.String getModProId() {
            return localModProId;
        }

         
        public void setModProId(java.lang.String param) {
            localModProIdTracker = param != null;

            this.localModProId = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://project.funnel.api.manage.hps.com/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":basicDto", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "basicDto", xmlWriter);
            }

            if (localBatchDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "batchDate", xmlWriter);

                if (localBatchDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "batchDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBatchDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localCreProIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "creProId", xmlWriter);

                if (localCreProId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "creProId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreProId);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdBy", xmlWriter);

                if (localCreatedBy == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdBy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedBy);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdDate", xmlWriter);

                if (localCreatedDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localCreatedDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localDeletedTracker) {
                namespace = "";
                writeStartElement(null, namespace, "deleted", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "deleted cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localDeleted));
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedBy", xmlWriter);

                if (localLastModifiedBy == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedBy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedBy);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedDate", xmlWriter);

                if (localLastModifiedDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localLastModifiedDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localModProIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "modProId", xmlWriter);

                if (localModProId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "modProId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localModProId);
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static BasicDto parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                BasicDto object = null;

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"basicDto".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (BasicDto) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }

                            throw new org.apache.axis2.databinding.ADBException(
                                "The an abstract class can not be instantiated !!!");
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "batchDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "batchDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBatchDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "creProId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "creProId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreProId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdBy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdBy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedBy(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "deleted").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "deleted" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDeleted(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedBy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedBy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedBy(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "modProId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "modProId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setModProId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class ProjectRThreeSaveParam extends ToString implements org.apache.axis2.databinding.ADBBean {
         

         
        protected java.lang.String localAdverseContractSubjectCode;

         
        protected boolean localAdverseContractSubjectCodeTracker = false;

         
        protected java.lang.String localAdverseContractSubjectId;

         
        protected boolean localAdverseContractSubjectIdTracker = false;

         
        protected java.lang.String localAdverseContractSubjectName;

         
        protected boolean localAdverseContractSubjectNameTracker = false;

         
        protected java.lang.String localAfterSalesManagerCode;

         
        protected boolean localAfterSalesManagerCodeTracker = false;

         
        protected java.lang.String localAfterSalesManagerId;

         
        protected boolean localAfterSalesManagerIdTracker = false;

         
        protected java.lang.String localAfterSalesManagerName;

         
        protected boolean localAfterSalesManagerNameTracker = false;

         
        protected java.lang.String localBidCompanyCode;

         
        protected boolean localBidCompanyCodeTracker = false;

         
        protected java.lang.String localBidCompanyId;

         
        protected boolean localBidCompanyIdTracker = false;

         
        protected java.lang.String localBidCompanyName;

         
        protected boolean localBidCompanyNameTracker = false;

         
        protected java.lang.String localBigChannel;

         
        protected boolean localBigChannelTracker = false;

         
        protected java.lang.String localContractorCode;

         
        protected boolean localContractorCodeTracker = false;

         
        protected java.lang.String localContractorId;

         
        protected boolean localContractorIdTracker = false;

         
        protected java.lang.String localContractorName;

         
        protected boolean localContractorNameTracker = false;

         
        protected java.lang.String localCreatedById;

         
        protected boolean localCreatedByIdTracker = false;

         
        protected java.lang.String localCustomerDemandAnalysis;

         
        protected boolean localCustomerDemandAnalysisTracker = false;

         
        protected java.lang.String localDesignManagerCode;

         
        protected boolean localDesignManagerCodeTracker = false;

         
        protected java.lang.String localDesignManagerId;

         
        protected boolean localDesignManagerIdTracker = false;

         
        protected java.lang.String localDesignManagerName;

         
        protected boolean localDesignManagerNameTracker = false;

         
        protected java.lang.String localDesignType;

         
        protected boolean localDesignTypeTracker = false;

         
        protected java.lang.String localGeneralContractorCode;

         
        protected boolean localGeneralContractorCodeTracker = false;

         
        protected java.lang.String localGeneralContractorId;

         
        protected boolean localGeneralContractorIdTracker = false;

         
        protected java.lang.String localGeneralContractorName;

         
        protected boolean localGeneralContractorNameTracker = false;

         
        protected java.lang.String localHonourAgreementPattern;

         
        protected boolean localHonourAgreementPatternTracker = false;

         
        protected java.lang.String localId;

         
        protected boolean localIdTracker = false;

         
        protected java.lang.String localInformationSubmissionType;

         
        protected boolean localInformationSubmissionTypeTracker = false;

         
        protected java.lang.String localInstallationType;

         
        protected boolean localInstallationTypeTracker = false;

         
        protected java.lang.String localLastModifiedById;

         
        protected boolean localLastModifiedByIdTracker = false;

         
        protected java.lang.String localOurContractSubjectCode;

         
        protected boolean localOurContractSubjectCodeTracker = false;

         
        protected java.lang.String localOurContractSubjectId;

         
        protected boolean localOurContractSubjectIdTracker = false;

         
        protected java.lang.String localOurContractSubjectName;

         
        protected boolean localOurContractSubjectNameTracker = false;

         
        protected java.lang.String localProjectId;

         
        protected boolean localProjectIdTracker = false;

         
        protected java.lang.String localProjectType;

         
        protected boolean localProjectTypeTracker = false;

         
        protected java.lang.String localSmallChannel;

         
        protected boolean localSmallChannelTracker = false;

        public boolean isAdverseContractSubjectCodeSpecified() {
            return localAdverseContractSubjectCodeTracker;
        }

         
        public java.lang.String getAdverseContractSubjectCode() {
            return localAdverseContractSubjectCode;
        }

         
        public void setAdverseContractSubjectCode(java.lang.String param) {
            localAdverseContractSubjectCodeTracker = param != null;

            this.localAdverseContractSubjectCode = param;
        }

        public boolean isAdverseContractSubjectIdSpecified() {
            return localAdverseContractSubjectIdTracker;
        }

         
        public java.lang.String getAdverseContractSubjectId() {
            return localAdverseContractSubjectId;
        }

         
        public void setAdverseContractSubjectId(java.lang.String param) {
            localAdverseContractSubjectIdTracker = param != null;

            this.localAdverseContractSubjectId = param;
        }

        public boolean isAdverseContractSubjectNameSpecified() {
            return localAdverseContractSubjectNameTracker;
        }

         
        public java.lang.String getAdverseContractSubjectName() {
            return localAdverseContractSubjectName;
        }

         
        public void setAdverseContractSubjectName(java.lang.String param) {
            localAdverseContractSubjectNameTracker = param != null;

            this.localAdverseContractSubjectName = param;
        }

        public boolean isAfterSalesManagerCodeSpecified() {
            return localAfterSalesManagerCodeTracker;
        }

         
        public java.lang.String getAfterSalesManagerCode() {
            return localAfterSalesManagerCode;
        }

         
        public void setAfterSalesManagerCode(java.lang.String param) {
            localAfterSalesManagerCodeTracker = param != null;

            this.localAfterSalesManagerCode = param;
        }

        public boolean isAfterSalesManagerIdSpecified() {
            return localAfterSalesManagerIdTracker;
        }

         
        public java.lang.String getAfterSalesManagerId() {
            return localAfterSalesManagerId;
        }

         
        public void setAfterSalesManagerId(java.lang.String param) {
            localAfterSalesManagerIdTracker = param != null;

            this.localAfterSalesManagerId = param;
        }

        public boolean isAfterSalesManagerNameSpecified() {
            return localAfterSalesManagerNameTracker;
        }

         
        public java.lang.String getAfterSalesManagerName() {
            return localAfterSalesManagerName;
        }

         
        public void setAfterSalesManagerName(java.lang.String param) {
            localAfterSalesManagerNameTracker = param != null;

            this.localAfterSalesManagerName = param;
        }

        public boolean isBidCompanyCodeSpecified() {
            return localBidCompanyCodeTracker;
        }

         
        public java.lang.String getBidCompanyCode() {
            return localBidCompanyCode;
        }

         
        public void setBidCompanyCode(java.lang.String param) {
            localBidCompanyCodeTracker = param != null;

            this.localBidCompanyCode = param;
        }

        public boolean isBidCompanyIdSpecified() {
            return localBidCompanyIdTracker;
        }

         
        public java.lang.String getBidCompanyId() {
            return localBidCompanyId;
        }

         
        public void setBidCompanyId(java.lang.String param) {
            localBidCompanyIdTracker = param != null;

            this.localBidCompanyId = param;
        }

        public boolean isBidCompanyNameSpecified() {
            return localBidCompanyNameTracker;
        }

         
        public java.lang.String getBidCompanyName() {
            return localBidCompanyName;
        }

         
        public void setBidCompanyName(java.lang.String param) {
            localBidCompanyNameTracker = param != null;

            this.localBidCompanyName = param;
        }

        public boolean isBigChannelSpecified() {
            return localBigChannelTracker;
        }

         
        public java.lang.String getBigChannel() {
            return localBigChannel;
        }

         
        public void setBigChannel(java.lang.String param) {
            localBigChannelTracker = param != null;

            this.localBigChannel = param;
        }

        public boolean isContractorCodeSpecified() {
            return localContractorCodeTracker;
        }

         
        public java.lang.String getContractorCode() {
            return localContractorCode;
        }

         
        public void setContractorCode(java.lang.String param) {
            localContractorCodeTracker = param != null;

            this.localContractorCode = param;
        }

        public boolean isContractorIdSpecified() {
            return localContractorIdTracker;
        }

         
        public java.lang.String getContractorId() {
            return localContractorId;
        }

         
        public void setContractorId(java.lang.String param) {
            localContractorIdTracker = param != null;

            this.localContractorId = param;
        }

        public boolean isContractorNameSpecified() {
            return localContractorNameTracker;
        }

         
        public java.lang.String getContractorName() {
            return localContractorName;
        }

         
        public void setContractorName(java.lang.String param) {
            localContractorNameTracker = param != null;

            this.localContractorName = param;
        }

        public boolean isCreatedByIdSpecified() {
            return localCreatedByIdTracker;
        }

         
        public java.lang.String getCreatedById() {
            return localCreatedById;
        }

         
        public void setCreatedById(java.lang.String param) {
            localCreatedByIdTracker = param != null;

            this.localCreatedById = param;
        }

        public boolean isCustomerDemandAnalysisSpecified() {
            return localCustomerDemandAnalysisTracker;
        }

         
        public java.lang.String getCustomerDemandAnalysis() {
            return localCustomerDemandAnalysis;
        }

         
        public void setCustomerDemandAnalysis(java.lang.String param) {
            localCustomerDemandAnalysisTracker = param != null;

            this.localCustomerDemandAnalysis = param;
        }

        public boolean isDesignManagerCodeSpecified() {
            return localDesignManagerCodeTracker;
        }

         
        public java.lang.String getDesignManagerCode() {
            return localDesignManagerCode;
        }

         
        public void setDesignManagerCode(java.lang.String param) {
            localDesignManagerCodeTracker = param != null;

            this.localDesignManagerCode = param;
        }

        public boolean isDesignManagerIdSpecified() {
            return localDesignManagerIdTracker;
        }

         
        public java.lang.String getDesignManagerId() {
            return localDesignManagerId;
        }

         
        public void setDesignManagerId(java.lang.String param) {
            localDesignManagerIdTracker = param != null;

            this.localDesignManagerId = param;
        }

        public boolean isDesignManagerNameSpecified() {
            return localDesignManagerNameTracker;
        }

         
        public java.lang.String getDesignManagerName() {
            return localDesignManagerName;
        }

         
        public void setDesignManagerName(java.lang.String param) {
            localDesignManagerNameTracker = param != null;

            this.localDesignManagerName = param;
        }

        public boolean isDesignTypeSpecified() {
            return localDesignTypeTracker;
        }

         
        public java.lang.String getDesignType() {
            return localDesignType;
        }

         
        public void setDesignType(java.lang.String param) {
            localDesignTypeTracker = param != null;

            this.localDesignType = param;
        }

        public boolean isGeneralContractorCodeSpecified() {
            return localGeneralContractorCodeTracker;
        }

         
        public java.lang.String getGeneralContractorCode() {
            return localGeneralContractorCode;
        }

         
        public void setGeneralContractorCode(java.lang.String param) {
            localGeneralContractorCodeTracker = param != null;

            this.localGeneralContractorCode = param;
        }

        public boolean isGeneralContractorIdSpecified() {
            return localGeneralContractorIdTracker;
        }

         
        public java.lang.String getGeneralContractorId() {
            return localGeneralContractorId;
        }

         
        public void setGeneralContractorId(java.lang.String param) {
            localGeneralContractorIdTracker = param != null;

            this.localGeneralContractorId = param;
        }

        public boolean isGeneralContractorNameSpecified() {
            return localGeneralContractorNameTracker;
        }

         
        public java.lang.String getGeneralContractorName() {
            return localGeneralContractorName;
        }

         
        public void setGeneralContractorName(java.lang.String param) {
            localGeneralContractorNameTracker = param != null;

            this.localGeneralContractorName = param;
        }

        public boolean isHonourAgreementPatternSpecified() {
            return localHonourAgreementPatternTracker;
        }

         
        public java.lang.String getHonourAgreementPattern() {
            return localHonourAgreementPattern;
        }

         
        public void setHonourAgreementPattern(java.lang.String param) {
            localHonourAgreementPatternTracker = param != null;

            this.localHonourAgreementPattern = param;
        }

        public boolean isIdSpecified() {
            return localIdTracker;
        }

         
        public java.lang.String getId() {
            return localId;
        }

         
        public void setId(java.lang.String param) {
            localIdTracker = param != null;

            this.localId = param;
        }

        public boolean isInformationSubmissionTypeSpecified() {
            return localInformationSubmissionTypeTracker;
        }

         
        public java.lang.String getInformationSubmissionType() {
            return localInformationSubmissionType;
        }

         
        public void setInformationSubmissionType(java.lang.String param) {
            localInformationSubmissionTypeTracker = param != null;

            this.localInformationSubmissionType = param;
        }

        public boolean isInstallationTypeSpecified() {
            return localInstallationTypeTracker;
        }

         
        public java.lang.String getInstallationType() {
            return localInstallationType;
        }

         
        public void setInstallationType(java.lang.String param) {
            localInstallationTypeTracker = param != null;

            this.localInstallationType = param;
        }

        public boolean isLastModifiedByIdSpecified() {
            return localLastModifiedByIdTracker;
        }

         
        public java.lang.String getLastModifiedById() {
            return localLastModifiedById;
        }

         
        public void setLastModifiedById(java.lang.String param) {
            localLastModifiedByIdTracker = param != null;

            this.localLastModifiedById = param;
        }

        public boolean isOurContractSubjectCodeSpecified() {
            return localOurContractSubjectCodeTracker;
        }

         
        public java.lang.String getOurContractSubjectCode() {
            return localOurContractSubjectCode;
        }

         
        public void setOurContractSubjectCode(java.lang.String param) {
            localOurContractSubjectCodeTracker = param != null;

            this.localOurContractSubjectCode = param;
        }

        public boolean isOurContractSubjectIdSpecified() {
            return localOurContractSubjectIdTracker;
        }

         
        public java.lang.String getOurContractSubjectId() {
            return localOurContractSubjectId;
        }

         
        public void setOurContractSubjectId(java.lang.String param) {
            localOurContractSubjectIdTracker = param != null;

            this.localOurContractSubjectId = param;
        }

        public boolean isOurContractSubjectNameSpecified() {
            return localOurContractSubjectNameTracker;
        }

         
        public java.lang.String getOurContractSubjectName() {
            return localOurContractSubjectName;
        }

         
        public void setOurContractSubjectName(java.lang.String param) {
            localOurContractSubjectNameTracker = param != null;

            this.localOurContractSubjectName = param;
        }

        public boolean isProjectIdSpecified() {
            return localProjectIdTracker;
        }

         
        public java.lang.String getProjectId() {
            return localProjectId;
        }

         
        public void setProjectId(java.lang.String param) {
            localProjectIdTracker = param != null;

            this.localProjectId = param;
        }

        public boolean isProjectTypeSpecified() {
            return localProjectTypeTracker;
        }

         
        public java.lang.String getProjectType() {
            return localProjectType;
        }

         
        public void setProjectType(java.lang.String param) {
            localProjectTypeTracker = param != null;

            this.localProjectType = param;
        }

        public boolean isSmallChannelSpecified() {
            return localSmallChannelTracker;
        }

         
        public java.lang.String getSmallChannel() {
            return localSmallChannel;
        }

         
        public void setSmallChannel(java.lang.String param) {
            localSmallChannelTracker = param != null;

            this.localSmallChannel = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://project.funnel.api.manage.hps.com/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":projectRThreeSaveParam", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "projectRThreeSaveParam", xmlWriter);
            }

            if (localAdverseContractSubjectCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace,
                    "adverseContractSubjectCode", xmlWriter);

                if (localAdverseContractSubjectCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "adverseContractSubjectCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAdverseContractSubjectCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localAdverseContractSubjectIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "adverseContractSubjectId",
                    xmlWriter);

                if (localAdverseContractSubjectId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "adverseContractSubjectId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAdverseContractSubjectId);
                }

                xmlWriter.writeEndElement();
            }

            if (localAdverseContractSubjectNameTracker) {
                namespace = "";
                writeStartElement(null, namespace,
                    "adverseContractSubjectName", xmlWriter);

                if (localAdverseContractSubjectName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "adverseContractSubjectName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAdverseContractSubjectName);
                }

                xmlWriter.writeEndElement();
            }

            if (localAfterSalesManagerCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "afterSalesManagerCode",
                    xmlWriter);

                if (localAfterSalesManagerCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "afterSalesManagerCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAfterSalesManagerCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localAfterSalesManagerIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "afterSalesManagerId",
                    xmlWriter);

                if (localAfterSalesManagerId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "afterSalesManagerId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAfterSalesManagerId);
                }

                xmlWriter.writeEndElement();
            }

            if (localAfterSalesManagerNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "afterSalesManagerName",
                    xmlWriter);

                if (localAfterSalesManagerName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "afterSalesManagerName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localAfterSalesManagerName);
                }

                xmlWriter.writeEndElement();
            }

            if (localBidCompanyCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "bidCompanyCode", xmlWriter);

                if (localBidCompanyCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "bidCompanyCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localBidCompanyCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localBidCompanyIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "bidCompanyId", xmlWriter);

                if (localBidCompanyId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "bidCompanyId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localBidCompanyId);
                }

                xmlWriter.writeEndElement();
            }

            if (localBidCompanyNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "bidCompanyName", xmlWriter);

                if (localBidCompanyName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "bidCompanyName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localBidCompanyName);
                }

                xmlWriter.writeEndElement();
            }

            if (localBigChannelTracker) {
                namespace = "";
                writeStartElement(null, namespace, "bigChannel", xmlWriter);

                if (localBigChannel == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "bigChannel cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localBigChannel);
                }

                xmlWriter.writeEndElement();
            }

            if (localContractorCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "contractorCode", xmlWriter);

                if (localContractorCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "contractorCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localContractorCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localContractorIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "contractorId", xmlWriter);

                if (localContractorId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "contractorId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localContractorId);
                }

                xmlWriter.writeEndElement();
            }

            if (localContractorNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "contractorName", xmlWriter);

                if (localContractorName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "contractorName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localContractorName);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdById", xmlWriter);

                if (localCreatedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localCustomerDemandAnalysisTracker) {
                namespace = "";
                writeStartElement(null, namespace, "customerDemandAnalysis",
                    xmlWriter);

                if (localCustomerDemandAnalysis == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "customerDemandAnalysis cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCustomerDemandAnalysis);
                }

                xmlWriter.writeEndElement();
            }

            if (localDesignManagerCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "designManagerCode",
                    xmlWriter);

                if (localDesignManagerCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "designManagerCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDesignManagerCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localDesignManagerIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "designManagerId", xmlWriter);

                if (localDesignManagerId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "designManagerId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDesignManagerId);
                }

                xmlWriter.writeEndElement();
            }

            if (localDesignManagerNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "designManagerName",
                    xmlWriter);

                if (localDesignManagerName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "designManagerName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDesignManagerName);
                }

                xmlWriter.writeEndElement();
            }

            if (localDesignTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "designType", xmlWriter);

                if (localDesignType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "designType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localDesignType);
                }

                xmlWriter.writeEndElement();
            }

            if (localGeneralContractorCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "generalContractorCode",
                    xmlWriter);

                if (localGeneralContractorCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "generalContractorCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGeneralContractorCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localGeneralContractorIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "generalContractorId",
                    xmlWriter);

                if (localGeneralContractorId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "generalContractorId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGeneralContractorId);
                }

                xmlWriter.writeEndElement();
            }

            if (localGeneralContractorNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "generalContractorName",
                    xmlWriter);

                if (localGeneralContractorName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "generalContractorName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localGeneralContractorName);
                }

                xmlWriter.writeEndElement();
            }

            if (localHonourAgreementPatternTracker) {
                namespace = "";
                writeStartElement(null, namespace, "honourAgreementPattern",
                    xmlWriter);

                if (localHonourAgreementPattern == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "honourAgreementPattern cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localHonourAgreementPattern);
                }

                xmlWriter.writeEndElement();
            }

            if (localIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "id", xmlWriter);

                if (localId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "id cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localId);
                }

                xmlWriter.writeEndElement();
            }

            if (localInformationSubmissionTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "informationSubmissionType",
                    xmlWriter);

                if (localInformationSubmissionType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "informationSubmissionType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localInformationSubmissionType);
                }

                xmlWriter.writeEndElement();
            }

            if (localInstallationTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "installationType", xmlWriter);

                if (localInstallationType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "installationType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localInstallationType);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedById", xmlWriter);

                if (localLastModifiedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localOurContractSubjectCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "ourContractSubjectCode",
                    xmlWriter);

                if (localOurContractSubjectCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "ourContractSubjectCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localOurContractSubjectCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localOurContractSubjectIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "ourContractSubjectId",
                    xmlWriter);

                if (localOurContractSubjectId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "ourContractSubjectId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localOurContractSubjectId);
                }

                xmlWriter.writeEndElement();
            }

            if (localOurContractSubjectNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "ourContractSubjectName",
                    xmlWriter);

                if (localOurContractSubjectName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "ourContractSubjectName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localOurContractSubjectName);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectId", xmlWriter);

                if (localProjectId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectId);
                }

                xmlWriter.writeEndElement();
            }

            if (localProjectTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "projectType", xmlWriter);

                if (localProjectType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "projectType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localProjectType);
                }

                xmlWriter.writeEndElement();
            }

            if (localSmallChannelTracker) {
                namespace = "";
                writeStartElement(null, namespace, "smallChannel", xmlWriter);

                if (localSmallChannel == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "smallChannel cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localSmallChannel);
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static ProjectRThreeSaveParam parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                ProjectRThreeSaveParam object = new ProjectRThreeSaveParam();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"projectRThreeSaveParam".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (ProjectRThreeSaveParam) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "adverseContractSubjectCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "adverseContractSubjectCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAdverseContractSubjectCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "adverseContractSubjectId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "adverseContractSubjectId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAdverseContractSubjectId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "adverseContractSubjectName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "adverseContractSubjectName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAdverseContractSubjectName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "afterSalesManagerCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "afterSalesManagerCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAfterSalesManagerCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "afterSalesManagerId").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "afterSalesManagerId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAfterSalesManagerId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "afterSalesManagerName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "afterSalesManagerName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setAfterSalesManagerName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "bidCompanyCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "bidCompanyCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBidCompanyCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "bidCompanyId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "bidCompanyId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBidCompanyId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "bidCompanyName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "bidCompanyName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBidCompanyName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "bigChannel").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "bigChannel" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBigChannel(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "contractorCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "contractorCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setContractorCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "contractorId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "contractorId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setContractorId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "contractorName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "contractorName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setContractorName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "customerDemandAnalysis").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "customerDemandAnalysis" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCustomerDemandAnalysis(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "designManagerCode").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "designManagerCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDesignManagerCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "designManagerId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "designManagerId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDesignManagerId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "designManagerName").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "designManagerName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDesignManagerName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "designType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "designType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDesignType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "generalContractorCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "generalContractorCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGeneralContractorCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "generalContractorId").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "generalContractorId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGeneralContractorId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "generalContractorName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "generalContractorName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setGeneralContractorName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "honourAgreementPattern").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "honourAgreementPattern" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setHonourAgreementPattern(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "id").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "id" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "informationSubmissionType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "informationSubmissionType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setInformationSubmissionType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "installationType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "installationType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setInstallationType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "ourContractSubjectCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "ourContractSubjectCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setOurContractSubjectCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "ourContractSubjectId").equals(reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "ourContractSubjectId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setOurContractSubjectId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("",
                                "ourContractSubjectName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "ourContractSubjectName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setOurContractSubjectName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "projectType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "projectType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setProjectType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "smallChannel").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "smallChannel" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setSmallChannel(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class BasePostTagContentDTO extends BasicDto implements org.apache.axis2.databinding.ADBBean {
         

         
        protected java.lang.String localBasePostCode;

         
        protected boolean localBasePostCodeTracker = false;

         
        protected java.lang.String localBasePostId;

         
        protected boolean localBasePostIdTracker = false;

         
        protected java.lang.String localCreatedById;

         
        protected boolean localCreatedByIdTracker = false;

         
        protected java.lang.String localId;

         
        protected boolean localIdTracker = false;

         
        protected java.lang.String localLastModifiedById;

         
        protected boolean localLastModifiedByIdTracker = false;

         
        protected java.lang.String localStandardPostCode;

         
        protected boolean localStandardPostCodeTracker = false;

         
        protected java.lang.String localStandardPostId;

         
        protected boolean localStandardPostIdTracker = false;

         
        protected java.lang.String localTagCode;

         
        protected boolean localTagCodeTracker = false;

         
        protected java.lang.String localTagContentDesc;

         
        protected boolean localTagContentDescTracker = false;

         
        protected java.lang.String localTagContentId;

         
        protected boolean localTagContentIdTracker = false;

         
        protected java.lang.String localTagContentName;

         
        protected boolean localTagContentNameTracker = false;

         
        protected java.lang.String localTagContentType;

         
        protected boolean localTagContentTypeTracker = false;

         
        protected java.lang.String localTagId;

         
        protected boolean localTagIdTracker = false;

        public boolean isBasePostCodeSpecified() {
            return localBasePostCodeTracker;
        }

         
        public java.lang.String getBasePostCode() {
            return localBasePostCode;
        }

         
        public void setBasePostCode(java.lang.String param) {
            localBasePostCodeTracker = param != null;

            this.localBasePostCode = param;
        }

        public boolean isBasePostIdSpecified() {
            return localBasePostIdTracker;
        }

         
        public java.lang.String getBasePostId() {
            return localBasePostId;
        }

         
        public void setBasePostId(java.lang.String param) {
            localBasePostIdTracker = param != null;

            this.localBasePostId = param;
        }

        public boolean isCreatedByIdSpecified() {
            return localCreatedByIdTracker;
        }

         
        public java.lang.String getCreatedById() {
            return localCreatedById;
        }

         
        public void setCreatedById(java.lang.String param) {
            localCreatedByIdTracker = param != null;

            this.localCreatedById = param;
        }

        public boolean isIdSpecified() {
            return localIdTracker;
        }

         
        public java.lang.String getId() {
            return localId;
        }

         
        public void setId(java.lang.String param) {
            localIdTracker = param != null;

            this.localId = param;
        }

        public boolean isLastModifiedByIdSpecified() {
            return localLastModifiedByIdTracker;
        }

         
        public java.lang.String getLastModifiedById() {
            return localLastModifiedById;
        }

         
        public void setLastModifiedById(java.lang.String param) {
            localLastModifiedByIdTracker = param != null;

            this.localLastModifiedById = param;
        }

        public boolean isStandardPostCodeSpecified() {
            return localStandardPostCodeTracker;
        }

         
        public java.lang.String getStandardPostCode() {
            return localStandardPostCode;
        }

         
        public void setStandardPostCode(java.lang.String param) {
            localStandardPostCodeTracker = param != null;

            this.localStandardPostCode = param;
        }

        public boolean isStandardPostIdSpecified() {
            return localStandardPostIdTracker;
        }

         
        public java.lang.String getStandardPostId() {
            return localStandardPostId;
        }

         
        public void setStandardPostId(java.lang.String param) {
            localStandardPostIdTracker = param != null;

            this.localStandardPostId = param;
        }

        public boolean isTagCodeSpecified() {
            return localTagCodeTracker;
        }

         
        public java.lang.String getTagCode() {
            return localTagCode;
        }

         
        public void setTagCode(java.lang.String param) {
            localTagCodeTracker = param != null;

            this.localTagCode = param;
        }

        public boolean isTagContentDescSpecified() {
            return localTagContentDescTracker;
        }

         
        public java.lang.String getTagContentDesc() {
            return localTagContentDesc;
        }

         
        public void setTagContentDesc(java.lang.String param) {
            localTagContentDescTracker = param != null;

            this.localTagContentDesc = param;
        }

        public boolean isTagContentIdSpecified() {
            return localTagContentIdTracker;
        }

         
        public java.lang.String getTagContentId() {
            return localTagContentId;
        }

         
        public void setTagContentId(java.lang.String param) {
            localTagContentIdTracker = param != null;

            this.localTagContentId = param;
        }

        public boolean isTagContentNameSpecified() {
            return localTagContentNameTracker;
        }

         
        public java.lang.String getTagContentName() {
            return localTagContentName;
        }

         
        public void setTagContentName(java.lang.String param) {
            localTagContentNameTracker = param != null;

            this.localTagContentName = param;
        }

        public boolean isTagContentTypeSpecified() {
            return localTagContentTypeTracker;
        }

         
        public java.lang.String getTagContentType() {
            return localTagContentType;
        }

         
        public void setTagContentType(java.lang.String param) {
            localTagContentTypeTracker = param != null;

            this.localTagContentType = param;
        }

        public boolean isTagIdSpecified() {
            return localTagIdTracker;
        }

         
        public java.lang.String getTagId() {
            return localTagId;
        }

         
        public void setTagId(java.lang.String param) {
            localTagIdTracker = param != null;

            this.localTagId = param;
        }

         
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            java.lang.String prefix = null;
            java.lang.String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://project.funnel.api.manage.hps.com/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":basePostTagContentDTO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "basePostTagContentDTO", xmlWriter);
            }

            if (localBatchDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "batchDate", xmlWriter);

                if (localBatchDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "batchDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localBatchDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localCreProIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "creProId", xmlWriter);

                if (localCreProId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "creProId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreProId);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdBy", xmlWriter);

                if (localCreatedBy == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdBy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedBy);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdDate", xmlWriter);

                if (localCreatedDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localCreatedDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localDeletedTracker) {
                namespace = "";
                writeStartElement(null, namespace, "deleted", xmlWriter);

                if (false) {
                    throw new org.apache.axis2.databinding.ADBException(
                        "deleted cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localDeleted));
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedBy", xmlWriter);

                if (localLastModifiedBy == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedBy cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedBy);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedDateTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedDate", xmlWriter);

                if (localLastModifiedDate == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedDate cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            localLastModifiedDate));
                }

                xmlWriter.writeEndElement();
            }

            if (localModProIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "modProId", xmlWriter);

                if (localModProId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "modProId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localModProId);
                }

                xmlWriter.writeEndElement();
            }

            if (localBasePostCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "basePostCode", xmlWriter);

                if (localBasePostCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "basePostCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localBasePostCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localBasePostIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "basePostId", xmlWriter);

                if (localBasePostId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "basePostId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localBasePostId);
                }

                xmlWriter.writeEndElement();
            }

            if (localCreatedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "createdById", xmlWriter);

                if (localCreatedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "createdById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localCreatedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "id", xmlWriter);

                if (localId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "id cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localId);
                }

                xmlWriter.writeEndElement();
            }

            if (localLastModifiedByIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "lastModifiedById", xmlWriter);

                if (localLastModifiedById == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "lastModifiedById cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localLastModifiedById);
                }

                xmlWriter.writeEndElement();
            }

            if (localStandardPostCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "standardPostCode", xmlWriter);

                if (localStandardPostCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "standardPostCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localStandardPostCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localStandardPostIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "standardPostId", xmlWriter);

                if (localStandardPostId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "standardPostId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localStandardPostId);
                }

                xmlWriter.writeEndElement();
            }

            if (localTagCodeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "tagCode", xmlWriter);

                if (localTagCode == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "tagCode cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localTagCode);
                }

                xmlWriter.writeEndElement();
            }

            if (localTagContentDescTracker) {
                namespace = "";
                writeStartElement(null, namespace, "tagContentDesc", xmlWriter);

                if (localTagContentDesc == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "tagContentDesc cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localTagContentDesc);
                }

                xmlWriter.writeEndElement();
            }

            if (localTagContentIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "tagContentId", xmlWriter);

                if (localTagContentId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "tagContentId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localTagContentId);
                }

                xmlWriter.writeEndElement();
            }

            if (localTagContentNameTracker) {
                namespace = "";
                writeStartElement(null, namespace, "tagContentName", xmlWriter);

                if (localTagContentName == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "tagContentName cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localTagContentName);
                }

                xmlWriter.writeEndElement();
            }

            if (localTagContentTypeTracker) {
                namespace = "";
                writeStartElement(null, namespace, "tagContentType", xmlWriter);

                if (localTagContentType == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "tagContentType cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localTagContentType);
                }

                xmlWriter.writeEndElement();
            }

            if (localTagIdTracker) {
                namespace = "";
                writeStartElement(null, namespace, "tagId", xmlWriter);

                if (localTagId == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "tagId cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localTagId);
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static java.lang.String generatePrefix(
            java.lang.String namespace) {
            if (namespace.equals("http://project.funnel.api.manage.hps.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

         
        private void writeStartElement(java.lang.String prefix,
            java.lang.String namespace, java.lang.String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

         
        private void writeAttribute(java.lang.String prefix,
            java.lang.String namespace, java.lang.String attName,
            java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

         
        private void writeAttribute(java.lang.String namespace,
            java.lang.String attName, java.lang.String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

         
        private void writeQNameAttribute(java.lang.String namespace,
            java.lang.String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String attributeNamespace = qname.getNamespaceURI();
            java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            java.lang.String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

         
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

         
        private java.lang.String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            java.lang.String namespace)
            throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

         
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

             
            public static BasePostTagContentDTO parse(
                javax.xml.stream.XMLStreamReader reader)
                throws java.lang.Exception {
                BasePostTagContentDTO object = new BasePostTagContentDTO();

                int event;
                javax.xml.namespace.QName currentQName = null;
                java.lang.String nillableValue = null;
                java.lang.String prefix = "";
                java.lang.String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            java.lang.String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"basePostTagContentDTO".equals(type)) {
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (BasePostTagContentDTO) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "batchDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "batchDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBatchDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "creProId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "creProId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreProId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdBy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdBy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedBy(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "deleted").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "deleted" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setDeleted(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedBy").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedBy" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedBy(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedDate").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedDate" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "modProId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "modProId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setModProId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "basePostCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "basePostCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBasePostCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "basePostId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "basePostId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setBasePostId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "createdById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "createdById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setCreatedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "id").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "id" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "lastModifiedById").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "lastModifiedById" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setLastModifiedById(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "standardPostCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "standardPostCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setStandardPostCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "standardPostId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "standardPostId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setStandardPostId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "tagCode").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "tagCode" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setTagCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "tagContentDesc").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "tagContentDesc" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setTagContentDesc(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "tagContentId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "tagContentId" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setTagContentId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "tagContentName").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "tagContentName" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setTagContentName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "tagContentType").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "tagContentType" +
                                "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setTagContentType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "tagId").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "tagId" + "  cannot be null");
                        }

                        java.lang.String content = reader.getElementText();

                        object.setTagId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new java.lang.Exception(e);
                }

                return object;
            }
        } //end of factory class
    }
}
