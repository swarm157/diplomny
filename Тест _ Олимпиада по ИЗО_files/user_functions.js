function sendForm(id)
{
	var form_id="#"+id;
	console.log(form_id);
	$(form_id).ajaxSubmit({ 
		target: null,
		data: {'form':id},
		beforeSubmit: showRequestA,
		success: showResponseA,
		url: "/Admin/modules/form_saver.php",
		timeout: 10000
     });
	return false;
}

function sendInterview(id,num_answer,template)
{
	$.post("/Admin/modules/interview_saver.php", {id:id, num: num_answer,template:template}, function (data){
	$('#interview'+id).html(data);
	});
}

jQuery.extend({
    handleError: function( s, xhr, status, e ) {
        // If a local callback was specified, fire it
        showResponseA(xhr.responseText, status);
        /*if ( s.error )
            s.error( xhr, status, e );
        // If we have some XML response text (e.g. from an AJAX call) then log it in the console
        console.log('ok');
        else if(xhr.responseText)
		{
			var data=xhr.responseText;
			//alert(data);
			$('#transparent').hide();
			tmp=data.split(';');
			if (tmp[0]=='f')
			{
				
				
				if (tmp[2]==1)
				{
					var qString = $('#form'+tmp[1]).formSerialize();
					$.post("/Admin/modules/form_result.php", qString, function (data){$('#form'+tmp[1]).html(data);});
				}
				else
					{
						//alert('.field_name .'+data);
						if(tmp[2]!='captcha')
						{
						var field_name=$('.field_name').filter('.'+tmp[2]).html();
						field_name=field_name.replace('<span>*</span>','');
						alert('Вы не заполнили "'+field_name+'"');
						}
						else
						alert('Вы не заполнили "код с картинки"');
					}
			}
			else
			{
				if (tmp[0]=='c')
				{
					//alert(tmp[1]);
					eval(tmp[1]+'('+tmp[2]+')');
				}
				else
				{
					$('#div_user_foto').html(xhr.responseText);
				}
			}
		}*/
    }
});
function showRequestA(formData, jqForm, options) { 
      var queryString = $.param(formData); 
      console.log('Вот что мы передаем: \n\n' + queryString); 
	  $('body').append('<div id="transparent" style="background: rgba(0,0,0,0.5);display: block;height: 100%;left: 0;position: fixed;top: 0; bottom:0; width: 100%;z-index: 5; color:#fff; text-align:center;"><img style="margin:0 auto; display:block; margin-top:100px;" src="/Admin/images/loader.gif"><span>Отправка файлов на сервер...</span></div>');
      return true; 
  } 
 
function showResponseA(responseText, statusText)  { 
	//alert(responseText);
	var data=$.parseJSON(responseText);
	//console.log(data);
	$('#transparent').remove();
	//tmp=data.split(';');
	if (data.type=='form')
	{
		if (data.error.length==0)
		{
			//var qString = $('#form'+tmp[1]).formSerialize();
			var fid=data.form;
			console.log('ok');
			$.post("/Admin/modules/form_result.php", {id:data.item_id,form:data.id}, function (data){$('#'+fid).html(data);});
		}
		else
			{
				//alert('.field_name .'+data);
				$('#'+data.form+' input, #'+data.form+' select, #'+data.form+' textarea').removeClass('blank-data');
				for(var i=0;i<data.error.length;i++)
				{
					$('#'+data.form+' [name='+data.error[i]+']').addClass('blank-data');
				}
				alert('Заполнены не все обязательные поля!');
			}
	}
	else
	{
		if (tmp[0]=='c')
		{
			eval(tmp[1]+'('+tmp[2]+')');
		}
		else
		$('#div_user_foto').html(xhr.responseText);
	}
	return true;
  }
  
jQuery.fn.extend({
    insertAtCaret: function(myValue){
        return this.each(function(i) {
            if (document.selection) {
                // Для браузеров типа Internet Explorer
                this.focus();
                var sel = document.selection.createRange();
                sel.text = myValue;
                this.focus();
            }
            else if (this.selectionStart || this.selectionStart == '0') {
                // Для браузеров типа Firefox и других Webkit-ов
                var startPos = this.selectionStart;
                var endPos = this.selectionEnd;
                var scrollTop = this.scrollTop;
                this.value = this.value.substring(0, startPos)+myValue+this.value.substring(endPos,this.value.length);
                this.focus();
                this.selectionStart = startPos + myValue.length;
                this.selectionEnd = startPos + myValue.length;
                this.scrollTop = scrollTop;
            } else {
                this.value += myValue;
                this.focus();
            }
        })
    }
});

function insertHtmlAtCursor(html) {
    var sel, range;
	console.log(window.getSelection);
    if (window.getSelection) {
        // IE9 and non-IE
        sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
            range = sel.getRangeAt(0);
            range.deleteContents();

            // Range.createContextualFragment() would be useful here but is
            // non-standard and not supported in all browsers (IE9, for one)
            var el = document.createElement("div");
            el.innerHTML = html;
            var frag = document.createDocumentFragment(), node, lastNode;
            while ( (node = el.firstChild) ) {
                lastNode = frag.appendChild(node);
            }
            range.insertNode(frag);

            // Preserve the selection
            if (lastNode) {
                range = range.cloneRange();
                range.setStartAfter(lastNode);
                range.collapse(true);
                sel.removeAllRanges();
                sel.addRange(range);
            }
        }
    } else if (document.selection && document.selection.type != "Control") {
        // IE < 9
        document.selection.createRange().pasteHTML(html);
    }
}