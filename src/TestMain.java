import com.demo.service.OrderService;
import com.jfinal.aop.Duang;
import com.jfinal.aop.Enhancer;

public class TestMain {
    
	/**
	 * Duang.duang()、 Enhancer.enhance()与 Controller.enhance()系方法在功能上完全一样， 它们除了支持类增强以外， 还支持对象增强
	 * @param args
	 */
	public void main(String[] args) {
		// 使用Duang.duang方法在任何地方对目标进行增强
		OrderService service = Duang.duang(OrderService.class);
		
		//调用payment方法将触发拦截器
		service.payment(getParaToInt("orderId"), getParaToInt("userId"));
		
		// 使用Enhancer.enhance方法在任何地方对目标进行增强
		OrderService service2 = Enhancer.enhance(OrderService.class);
	}

	private int getParaToInt(String string) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
