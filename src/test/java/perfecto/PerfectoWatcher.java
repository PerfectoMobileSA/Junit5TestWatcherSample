package perfecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.perfecto.reportium.test.result.TestResultFactory;

public class PerfectoWatcher implements TestWatcher, AfterAllCallback {

	private List<TestResultStatus> testResultsStatus = new ArrayList<>();

	private enum TestResultStatus {
		SUCCESSFUL, ABORTED, FAILED, DISABLED;
	}

	@Override
	public void testDisabled(ExtensionContext context, Optional<String> reason) {
		System.out.println("Test Disabled for test: " + context.getDisplayName());
		testResultsStatus.add(TestResultStatus.DISABLED);
	}

	@Override
	public void testSuccessful(ExtensionContext context) {
		if (TestBase.driver == null) {
			return;
		}
		System.out.println("Test Successful for test: " + context.getDisplayName());
		TestBase.reportiumClient.testStop(TestResultFactory.createSuccess());
		testResultsStatus.add(TestResultStatus.SUCCESSFUL);
		TestBase.driver.quit();
	}

	@Override
	public void testAborted(ExtensionContext context, Throwable cause) {
		System.out.println("Test Aborted for test: " + context.getDisplayName());
		testResultsStatus.add(TestResultStatus.ABORTED);
	}

	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {
		if (TestBase.driver == null) {
			return;
		}
		System.out.println("Test Failed for test: " + context.getDisplayName() + " with reason: " + cause.getMessage());
		TestBase.reportiumClient.testStop(TestResultFactory.createFailure(cause));
		testResultsStatus.add(TestResultStatus.FAILED);
		TestBase.driver.quit();
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		Map<TestResultStatus, Long> summary = testResultsStatus.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println("Test result summary for " + context.getDisplayName() + ": " + summary.toString());
	}

}