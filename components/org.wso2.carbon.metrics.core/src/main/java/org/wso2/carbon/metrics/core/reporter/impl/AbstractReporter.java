/*
 * Copyright 2015 WSO2 Inc. (http://wso2.org)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.metrics.core.reporter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.metrics.core.reporter.Reporter;

/**
 * This abstract reporter implements {@link Reporter#start()} and {@link Reporter#stop()} methods. This class makes sure
 * that the reporter will not be started again if it is already running.
 *
 * @see Reporter
 */
public abstract class AbstractReporter implements Reporter {

    private static final Logger logger = LoggerFactory.getLogger(AbstractReporter.class);

    private volatile boolean running;

    private final String name;

    public AbstractReporter(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The reporter name cannot be null");
        }
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public final void start() {
        if (!running) {
            startReporter();
            running = true;
            if (logger.isDebugEnabled()) {
                logger.debug("Started '{}' reporter for Metrics", name);
            }
        }
    }

    public abstract void startReporter();

    @Override
    public final void stop() {
        if (running) {
            stopReporter();
            running = false;
            if (logger.isDebugEnabled()) {
                logger.debug("Stopped '{}' reporter for Metrics", name);
            }
        }
    }

    public abstract void stopReporter();

}
