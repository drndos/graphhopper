/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.util.spatialrules.CustomArea;
import com.graphhopper.storage.IntsRef;

import java.util.List;

/**
 * This interface defines how parts of the information from 'way' is converted into IntsRef. A TagParser usually
 * has one corresponding EncodedValue but more are possible too.
 */
public interface TagParser {

    void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> registerNewEncodedValue);

    IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, boolean ferry, IntsRef relationFlags);

    // todo: this way subclasses have to overwrite both methods in case they need to make use of custom areas, but on the
    // otherhand existing subclasses can remain unchanged...
    default IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, boolean ferry, IntsRef relationFlags, List<CustomArea> customAreas) {
        return handleWayTags(edgeFlags, way, ferry, relationFlags);
    }
}
