/*
 * Copyright 2012-2016 the original author or authors.
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

package org.springframework.boot.test.autoconfigure.web.servlet;

import org.springframework.boot.test.autoconfigure.web.servlet.SpringBootMockMvcBuilderCustomizer.DeferredLinesWriter;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * {@link TestExecutionListener} used to print MVC lines only on failure.
 *
 * @author Phillip Webb
 */
class MockMvcPrintOnlyOnFailureTestExecutionListener
		extends AbstractTestExecutionListener {

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		if (testContext.getTestException() != null) {
			DeferredLinesWriter writer = DeferredLinesWriter
					.get(testContext.getApplicationContext());
			if (writer != null) {
				writer.writeDeferredResult();
			}
		}

	}

}
