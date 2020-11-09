package com.example.okta_cust_sign_in.rx;

import com.okta.oidc.OktaBuilder;

/**
 * A collection of builders for creating different type of authentication clients.
 * {@link RxAuthClient}
 */
public class RxOkta {
    /**
     * The RX authentication client builder.
     */
    public static class AuthBuilder extends OktaBuilder<RxAuthClient, AuthBuilder> {
        @Override
        protected AuthBuilder toThis() {
            return this;
        }

        /**
         * Create AuthClient.
         *
         * @return the authenticate client {@link RxAuthClient}
         */
        @Override
        public RxAuthClient create() {
            super.withAuthenticationClientFactory(RxAuthClientImpl::new);
            return createAuthClient();
        }
    }

}
