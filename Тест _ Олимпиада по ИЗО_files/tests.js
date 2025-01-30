var test_p=false;
var right=0;
var wrong=0;
$(document).ready(function(){
	$('.test_button').click(function(){
		$('#restxt').html('');
		var el=$(this);
		test_p=false;
		if(parseInt($(this).attr('data'))>0)
		{
			$('h1').hide();
			$.post('/include/tests/next_q.php',{test:$(this).attr('test'),num:$(this).attr('data')},function(data){
				var res=$.parseJSON(data);
				el.attr('data',res.data);
				el.removeClass('big_t_b');
				if(res.data=='final')
					el.html('Завершить тест →');
				else
					el.html('Следующий вопрос →');
				if(res.multi=='1')
				{
					el.hide();
					$('.set_ans').show();
				}

				$('#test_content').html(res.text);
			});
		}
		else
		{
			$('h1').show();
			$.post('/include/tests/getResult.php',{test:$(this).attr('test'),r:right,w:wrong},function(data){
				el.hide();
				$('#test_content').html(data);
			});
		}
	});
	$('.set_ans').unbind('click');
	$('.set_ans').click(function(){
		if($('a.test_answer.blue').length>0)
		{
		var data={};
		test_p=true;
		data['var']=[];
		$('a.test_answer.blue').each(function(){
			data['q']=$(this).attr('q');
			data['var'][data['var'].length]=$(this).attr('num');
		});
		var dt=data['var'];
		//console.log(data);
		$('.set_ans').hide();
		$('.test_button').show();
		$('a.test_answer').addClass('nh');
		$.post('/include/tests/getAnswerM.php',{data:JSON.stringify(data)},function(data){
			var res=$.parseJSON(data);
			for(var i=0;i<res.right.length;i++)
			{
				$('a.test_answer[num='+res.right[i]+']').removeClass('nh');
				if($('a.test_answer[num='+res.right[i]+']').hasClass('blue'))
				{
					$('a.test_answer[num='+res.right[i]+']').removeClass('blue');
					$('a.test_answer[num='+res.right[i]+']').addClass('right_ans');
				}
				else
				{
					$('a.test_answer[num='+res.right[i]+']').addClass('mbright_ans');
				}
			}
			for(var i=0;i<res.error.length;i++)
			{
				$('a.test_answer[num='+res.error[i]+']').removeClass('nh');
				if($('a.test_answer[num='+res.error[i]+']').hasClass('blue'))
				{
					$('a.test_answer[num='+res.error[i]+']').removeClass('blue');
					$('a.test_answer[num='+res.error[i]+']').addClass('wrong_ans');
				}
			}
			if((res.error.length==0)&&(dt.length==res.right.length))
			{
				$('#restxt').html(res.rcomment);
				right++;
			}
			else
			{
				$('#restxt').html(res.ecomment);	
				wrong++;
			}
			console.log(right+' '+wrong);
		});
		}
	});
});

function setAnswer(el,multi)
{
	if(!test_p)
	{
		if(multi==0)
		{
			$('a.test_answer').removeAttr('href');
			$('a.test_answer').css('cursor','default');
			$('a.test_answer').addClass('black');
			test_p=true;
			$.post('/include/tests/getAnswer.php',{q:el.attr('q'),num:el.attr('num')},function(data){
				var res=$.parseJSON(data);
				$('p.test_answer_comment[num='+res.right+']').html(res.rcomment);
				$('a.test_answer[num='+res.right+']').addClass('right_ans');
				if(parseInt(res.error)!=0)
				{
					wrong++;
					$('a.test_answer[num='+res.error+']').addClass('wrong_ans');
					$('p.test_answer_comment[num='+res.error+']').html(res.ecomment);
				}
				else
					right++;
			});
		}
		else
		{
			if(el.hasClass('blue'))
				el.removeClass('blue');
			else
				el.addClass('blue');
				//$('a.test_answer').addClass('black');
		}
	}
}