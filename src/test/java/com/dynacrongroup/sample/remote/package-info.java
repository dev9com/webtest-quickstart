/**
 * Package contains tests that are designed to run against a remotely deployed webapp, like a QA or production server.
 *  These tests have the following features:
 *
 *  - Tests require that the application be deployed independently before running.  This is an external dependency.
 *  - Tests have the suffix "Test" so that they are picked up by Surefire in the test phase
 *  - Tests are directed at a remote server (www.dynacrongroup.com, in the samples shown)
 *  - Tests are run from the command line using the "test" phase - "mvn clean test", for example.
 *
 */
package com.dynacrongroup.sample.remote;