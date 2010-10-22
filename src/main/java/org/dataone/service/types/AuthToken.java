/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dataone.service.types;

/**
 * The DataONE Type to represent an authentication token.
 *
 * An AuthToken is used to identify a principal and to assert that
 * the identity of the principal has been verified
 * by the DataONE authentication infrastructure.
 * 
 * @author Matthew Jones
 */
public class AuthToken 
{
    private String token;



    /**
     * @param token the token to set
     */
    public AuthToken(String token) {
        this.token = token;
    }
    public AuthToken() {}
    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
