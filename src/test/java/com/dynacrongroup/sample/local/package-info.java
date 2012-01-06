/**
 * Package contains tests that are designed to run against a locally deployed webapp.  These tests have the following
 *  features:
 *
 *  - Tests require that the application be deployed to a local container before running.  This is handled by the
 *      cargo plugin, which loads the war file for the application in the "pre-integration" phase and shuts it down
 *      in the "post-integration" phase.
 *  - Tests have the suffix "IT" so that they are picked up by Failsafe in the integration-test phase
 *  - Tests are directed at "http://${WEBTEST_HOSTNAME}:8080/${context}/"
 *  - Tests are run from the command line using the "verify" phase - "mvn clean verify", for example.
 *
 */
package com.dynacrongroup.sample.local;