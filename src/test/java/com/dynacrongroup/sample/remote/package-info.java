/**
 * Package contains tests that are designed to run against a remotely deployed webapp, like a QA or production server.
 *  These tests have the following features:
 *
 *  - Tests require that the application be deployed independently before running.  This is an external dependency.
 *  - Tests could have the suffix "Test" so that they are picked up by Surefire in the test phase (but currently
 *      use *IT so that all tests are aggregated.)
 *  - Tests are directed at a remote server (www.dynacrongroup.com, in the samples shown)
 *
 */
package com.dynacrongroup.sample.remote;