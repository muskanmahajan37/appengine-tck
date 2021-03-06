/*
 * Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.appengine.tck.modules;

import com.google.appengine.tck.base.TestBase;
import com.google.appengine.tck.base.TestContext;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class ModulesTestBase extends TestBase {
    protected static TestContext toSubdeployment(int module) {
        TestContext context = new TestContext("module" + module).setSubdeployment(true);
        if (module > 1) {
            // only module #1 is default
            context.setAppEngineWebXmlFile(String.format("m%s-appengine-web.xml", module));
        }
        return context;
    }

    protected static WebArchive getTckSubDeployment(int module) {
        return getTckDeployment(toSubdeployment(module));
    }

    protected static EnterpriseArchive toEarDeployment(WebArchive... wars) {
        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "gae-tck.ear");
        for (WebArchive war : wars) {
            war.addClass(ModulesTestBase.class);
            ear.addAsModule(war);
        }
        return ear;
    }

    protected static EnterpriseArchive getEarDeployment(WebArchive... wars) {
        return getEarDeployment("application.xml", wars);
    }

    protected static EnterpriseArchive getEarDeployment(String applicationXml, WebArchive... wars) {
        return getEarDeployment(applicationXml, "appengine-application.xml", wars);
    }

    protected static EnterpriseArchive getEarDeployment(String applicationXml, String gaeApplicationXml, WebArchive... wars) {
        EnterpriseArchive ear = toEarDeployment(wars);
        ear.addAsManifestResource(applicationXml, "application.xml");
        ear.addAsResource(gaeApplicationXml, "appengine-application.xml");
        return ear;
    }
}
