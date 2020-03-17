package com.jhmis.config;

import com.kjtpay.gateway.common.util.security.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 快捷通配置
 */
@Configuration
@PropertySource("classpath:payment/kjt.properties")
public class KjtConfig {
    @Value("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJvB9K4LggojlZ78XCe6i9VToh6wtLLZWKZuif3Z6Jy9bTMcvULMBzH/j3KXPgZ/2Gp/hAS232bua+I30S9VxUn1zQ5zIftw9jhJFYzzUC4KkjNWgd3Z26l0ToUnh4OhS/HVM0ADobURt/2iF/YOR9nOr2qFDa78T0JtPCCbA6/7AgMBAAECgYEAiF55PkSfFwduwSLMAJCoSx8NKvelTY1Tu2u+bz3xFgIXcI+x+KCvkAvjaP2X9ZLLRPrd+E6wK+uFEyuxf1MGBkwme4y/BovFKQ9nNaKVXESx+1k0+NEZnd2pbzUKzcl0CVIfQPprrapyaFSiCklCgtNxn33CteAPYkWmTeKQd+kCQQDQ1qH6De8xLqSJadOT5WDP0FgrRi48I8r+CW0Gm0RXafnXCKrp7HGmDc/m+mmtK6dvyvn03nJMj3OQFfewFhWFAkEAvu6eCRKa/2xBEajkDrLY/7Y5gf3DjM6WYTbl7Ms8pEnCUczboVh2ZLMu3S2EQy2tQXUY5E1KsUBriA9eJ1TnfwJBAJgwAWQnaWru8arN+wipPM7UppwKjcD32IMePVx2I+yL5yzkPSExVHTNVfed/wraaC9YvKTMwoC2fWHAEHs67L0CQAt9gLU7EWjdsuzFJTRdPwHkjYFtLB+FLzeafivJ71RTvAYANhd3o2hcAC3/Vz5TuWF/Eohqvlv8bHw7sIbdbTECQHgDMg3YEP/0W8MD8b1YJPRseHZ+Y2eWEnWKp2sYwa4JeKk4Vz3hNZmqcJipmZ11RLuIRbzwSIOfjK0usC/xNF0=")
    private String privateKey;
    @Value("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCf6rBYHQJRXR8P1KYZPBKbuzygPLOv7TLQwwR7LFYEzS1DYEQZ1kt+2LLEcOdV6M762RBvPfdId2RrdSnl4TUcXe+qDkpsblugjNWn2CJkrCfwr0JYrnZlRNf6HZkjG3u07iXygWs1+eFuCVEz5/zQae1dW95qKrsGoo8AoJx9DQIDAQAB")
    private String publicKey;
    @Bean(name="securityServiceRsa")
    public SecurityService securityService() {
        SecurityService service = new SecurityService(privateKey, publicKey);
        return service;
    }
}
