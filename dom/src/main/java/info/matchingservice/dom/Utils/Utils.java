/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package info.matchingservice.dom.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by jodo on 18/06/15.
 */
public class Utils {

    public static String toObjectURI(final String OID){
        String[] parts = OID.split(Pattern.quote("[OID]"));
        String part1 = parts[0];
        String part2 = parts[1];
        String URI = "objects/".concat(part2).concat("/").concat(part1);
        return URI;
    }

    public static String toApiID(final String OID){
        String[] parts = OID.split(Pattern.quote("[OID]"));
        String part1 = parts[0];
        String ApiID = part1;
        return ApiID;
    }

    public static boolean isValidDate(String text) {
        if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
            return false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }
}
