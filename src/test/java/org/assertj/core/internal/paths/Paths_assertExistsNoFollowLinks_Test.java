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

import static org.assertj.core.error.ShouldExistNoFollow.shouldExistNoFollow;
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

public class Paths_assertExistsNoFollowLinks_Test extends PathsBaseTest {

  @ClassRule
  public static FileSystemResource resource = new FileSystemResource();

  private static Path existing;
  private static Path nonExisting;
  private static Path symlink;
  private static Path dangling;

  @BeforeClass
  public static void initPaths() throws IOException {
	final FileSystem fs = resource.getFileSystem();

	existing = fs.getPath("/existing");
	Files.createFile(existing);

	symlink = fs.getPath("/symlink");
	Files.createSymbolicLink(symlink, existing);

	nonExisting = fs.getPath("/nonExisting");
	dangling = fs.getPath("/dangling");
	Files.createSymbolicLink(dangling, nonExisting);
  }

  @Test
  public void should_fail_if_actual_is_null() {
	thrown.expectAssertionError(actualIsNull());
	paths.assertExistsNoFollowLinks(info, null);
  }

  @Test
  public void should_fail_if_actual_does_not_exist() {
	try {
	  paths.assertExistsNoFollowLinks(info, nonExisting);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldExistNoFollow(nonExisting));
	}
  }

  @Test
  public void should_succeed_if_actual_is_dangling_symlink() {
	paths.assertExistsNoFollowLinks(info, dangling);
  }

  @Test
  public void should_pass_if_actual_exists() {
	paths.assertExistsNoFollowLinks(info, existing);
  }

  @Test
  public void should_pass_if_actual_is_non_dangling_symlink() {
	paths.assertExistsNoFollowLinks(info, symlink);
  }
}
