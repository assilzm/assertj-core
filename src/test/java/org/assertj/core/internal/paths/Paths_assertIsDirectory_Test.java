/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.core.internal.paths;

import static org.assertj.core.error.ShouldBeDirectory.shouldBeDirectory;
import static org.assertj.core.error.ShouldExist.shouldExist;
import static org.assertj.core.test.TestFailures.wasExpectingAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.assertj.core.internal.PathsBaseTest;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public class Paths_assertIsDirectory_Test extends PathsBaseTest {

  @ClassRule
  public static FileSystemResource resource = new FileSystemResource();

  private static Path regularFile;
  private static Path symlink;

  private static Path nonExisting;
  private static Path dangling;

  private static Path directory;
  private static Path dirSymlink;

  @BeforeClass
  public static void initPaths() throws IOException {
	final FileSystem fs = resource.getFileSystem();

	regularFile = fs.getPath("/existing");
	symlink = fs.getPath("/symlink");
	Files.createFile(regularFile);
	Files.createSymbolicLink(symlink, regularFile);

	nonExisting = fs.getPath("/nonExisting");
	dangling = fs.getPath("/dangling");
	Files.createSymbolicLink(dangling, nonExisting);

	directory = fs.getPath("/dir");
	dirSymlink = fs.getPath("/dirsymlink");
	Files.createDirectory(directory);
	Files.createSymbolicLink(dirSymlink, directory);
  }

  @Test
  public void should_fail_if_actual_is_null() {
	thrown.expectAssertionError(actualIsNull());
	paths.assertIsDirectory(info, null);
  }

  @Test
  public void should_fail_with_notexists_if_actual_does_not_exist() {
	try {
	  paths.assertIsDirectory(info, nonExisting);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldExist(nonExisting));
	}
  }

  @Test
  public void should_fail_with_notexists_if_actual_is_dangling_symlink() {
	try {
	  paths.assertIsDirectory(info, dangling);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldExist(dangling));
	}
  }

  @Test
  public void should_fail_if_actual_is_regular_file() {
	try {
	  paths.assertIsDirectory(info, regularFile);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldBeDirectory(regularFile));
	}
  }

  @Test
  public void should_fail_if_actual_is_symlink_pointing_to_regular_file() {
	try {
	  paths.assertIsDirectory(info, symlink);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldBeDirectory(symlink));
	}
  }

  @Test
  public void should_succeed_if_target_is_directory() {
	paths.assertIsDirectory(info, directory);
  }

  @Test
  public void should_succeed_if_target_is_symlink_pointing_to_directory() {
	paths.assertIsDirectory(info, dirSymlink);
  }
}
