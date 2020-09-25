/*
 * Copyright 2020 hornd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package isoft.testing.utest.product.external;

import java.math.BigDecimal;

/**
 * A service that is exposed by a partner system.
 * 
 * The Exchange rate Service Impl should call the partner service 
 * via REST over HTTP to get the current exchange rate for a 
 * specified currency.
 * 
 * @author hornd
 */
public interface ExchangeRateService {
    
    /**
     * Currency conversion.
     * 
     * @param fromCurrency
     * @param  toCurrency
     * @return 
     */
    public BigDecimal getCurrentExchangeRateForCurrency(String fromCurrency, String toCurrency);
}
