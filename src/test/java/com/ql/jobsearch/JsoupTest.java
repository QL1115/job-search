package com.ql.jobsearch;


import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.rmi.server.RemoteRef;

import static org.junit.jupiter.api.Assertions.fail;

//@SpringBootTest
//public class JsoupTest {
//
//	private String URL = "https://www.google.com/search?sxsrf=ACYBGNRy9eX7kVZF2gy8iQ4wKMjlWdMIyA:1577708061195&ei=HeoJXqrIC6emmAW_l5aYCA&q=%E8%81%B7%E7%BC%BA&oq=%E8%81%B7%E7%BC%BA&gs_l=psy-ab.3..35i39l2j0l8.147217.148318..148427...1.0..0.80.531.8......0....1..gws-wiz.....10..35i362i39j0i131j0i3j0i5i30j0i5i10i30j0i8i30j0i19j0i13i30i19j0i13i10i30i19.OfT-PX_dBeY&uact=5&ibp=htl;jobs&sa=X&ved=2ahUKEwiSyLyLrd3mAhVryYsBHRwiBBsQiYsCKAB6BAgIEAM#htivrt=jobs&fpstate=tldetail&htitab=&htidocid=VXctjyBO1INe4ZPnAAAAAA%3D%3D";
//
//	@Test
//	public void whenAsynchronousGetRequest_thenCorrect() {
//
//		Request request = new Request.Builder()
//				.url(URL)
//				.build();
//
//		OkHttpClient client = new OkHttpClient();
//
//		Call call = client.newCall(request);
//		call.enqueue(new Callback() {
//			public void onResponse(Call call, Response response)
//					throws IOException {
//				// ...
//			}
//
//			public void onFailure(Call call, IOException e) {
//				fail();
//			}
//		});
//	}
//}
