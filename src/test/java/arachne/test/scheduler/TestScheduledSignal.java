package arachne.test.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arachne.dev.test.CallableStub;
import arachne.lib.pipeline.BooleanSink;
import arachne.lib.scheduler.ScheduledSignal;

public class TestScheduledSignal
{
	BooleanSink trigger;
	ScheduledSignal signal;
	CallableStub fireHandler;
	
	@BeforeEach
	void beforeEach() {
		trigger = new BooleanSink(false);
		signal = new ScheduledSignal(trigger);
		fireHandler = new CallableStub();
		
		signal.attach(fireHandler::call);
	}
	
	@AfterEach
	void afterEach() {
		signal.detachAll();
		
		trigger = null;
		signal = null;
		fireHandler = null;
	}
	
	@Test
	void singleCall() {
		assertEquals(0, fireHandler.getNumberOfCalls());
		
		signal.run();
		assertEquals(0, fireHandler.getNumberOfCalls());
		
		trigger.accept(true);
		assertEquals(0, fireHandler.getNumberOfCalls());
		
		signal.run();
		assertEquals(1, fireHandler.getNumberOfCalls());
		
		trigger.accept(false);
		signal.run();
		assertEquals(1, fireHandler.getNumberOfCalls());
	}
	
	@Test
	void multipleCalls() {
		assertEquals(0, fireHandler.getNumberOfCalls());
		
		trigger.accept(true);
		signal.run();
		assertEquals(1, fireHandler.getNumberOfCalls());
		
		trigger.accept(false);
		signal.run();
		assertEquals(1, fireHandler.getNumberOfCalls());
		
		trigger.accept(true);
		signal.run();
		assertEquals(2, fireHandler.getNumberOfCalls());
	}
}
