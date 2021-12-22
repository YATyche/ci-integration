package com.example.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import datadog.trace.api.DDTags;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/health")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void health() {
	}

	@GetMapping("/boom")
	public String boom() {
		solve();
		return "";
	}

	private void solve() {
		Tracer tracer = GlobalTracer.get();
		Span span = tracer.buildSpan("Solve puzzle")
				.withTag(DDTags.SERVICE_NAME, "puzzle-solver")
				.withTag(DDTags.RESOURCE_NAME, "solver")
				.start();
		try (Scope scope = tracer.activateSpan(span)) {
			System.out.println(5 / 0);
		} catch (Exception e) {
			throw e;
		} finally {
			span.finish();
		}
	}

}
