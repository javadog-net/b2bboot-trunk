window.onload = function () {
	var arr = [];
	var lucky = [];
	var a = [];
	tiancong = [];
	tiancongnew = [];
	// var list = [];
	var cent = document.getElementById("main");
	var ennt = document.getElementById('cnnt');
	var stop = false;
	var finish = false;
	var ul = document.getElementById("ul");
	//var a = new Array(10);
	console.log(9999999);
	console.log(a);
	// 进页面转码
	function getUrlParam() {
		var url = location.search;
		console.log(88888);
		var theRequest = new Object();
		if (url.indexOf("?") != -1) {
			var str = url.substr(1);
			var strs = str.split("&");
			for (var i = 0; i < strs.length; i++) {
				theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
			}
		}
		return theRequest;
	}
	var obj = getUrlParam();
	var activeid = obj.id;
	var meet = obj.meet;
	var zhongjiang = obj.num;
	var jiangxiang = obj.wardid;
	$('.prize').text(obj.name);
	// 发送请求
	// debugger;
	$.ajax({
		type: "POST",
		url: "b2b.haier.com/shop/api/web/app/draw/startdraw",
		dataType: 'json',
		async: false,
		data: {
			actvid: activeid,
			meetingid: meet
		},
		success: function (res) {
			console.log(res);
			console.log('zzz');
			console.log(zhongjiang);
               var shuliang = res.body.result.length;
               console.log(shuliang);
            if(zhongjiang>=shuliang){
            	alert("参与人数太少啦");
			}


			if (res.msg == "未有人参与此次活动！") {
				alert(res.msg);
				return false;
			}
			// debugger;
			list = res.body.result;

			/*function fnLuanXu(num) {
				list.sort(function () {
					return Math.random() > 0.5 ? -1 : 1;
				})
				return list;
			}
			fnLuanXu();
			
			*/
			//  debugger;
			ul.innerHTML += "<li>" + "<img src='img/haier.jpg'/> " + "</li>";
			for (let i = 0; i < list.length; i++) {
				if (list[i].avatar == null || list[i].avatar == '') {
					list[i].avatar = 'img/haier.jpg'
					// debugger;
				}
				if (list[i].prizeIdTab != null && list[i].prizeIdTab != '') {
					if (list[i].prizeIdTab == jiangxiang) {
						tiancong.push(list[i]);
						// continue;
						console.log(tiancong + "天聪天聪");
						// debugger;
					}
				} else {
					tiancongnew.push(list[i]);
				}
				var tel = list[i].userPhone;
				var reg = /^(\d{3})\d{4}(\d{4})$/;
				tel = tel.replace(reg, "$1****$2");
				list[i].userPhone = tel;
			}
			list.forEach(function (item) {
				ul.innerHTML += "<li>" + "<img src='" + item.avatar + "'/> " + "</li>";
			})
			console.log('ok')
			console.log(res);
			// debugger;
			aaa = list.length + 1;
		},
		error: function (err) {
			console.log(err);
			console.log('zzzz');
			// debugger;
		}
	});
	// div拖拽
	var div1 = document.getElementById("popup");
	div1.onmousedown = function (ev) {
		var oevent = ev || event;
		console.log(oevent);

		var distanceX = oevent.clientX - div1.offsetLeft;
		var distanceY = oevent.clientY - div1.offsetTop;

		document.onmousemove = function (ev) {
			var oevent = ev || event;
			div1.style.left = oevent.clientX - distanceX + 'px';
			div1.style.top = oevent.clientY - distanceY + 'px';
		};
		document.onmouseup = function () {
			document.onmousemove = null;
			document.onmouseup = null;
		};
	};
	// 点击开始抽奖
	document.getElementById('start').onclick = function () {
		var start = document.getElementById('start');
		var end = document.getElementById('end');
		var complete = document.getElementById('complete');
		stop = false;
		start.style.display = "none";
		complete.style.display = "none";
		end.style.display = "inline";
		ennt.innerHTML = "";
		startRaffle();
	}

	// 点击结束抽奖
	document.getElementById('end').onclick = function () {
		var start = document.getElementById('start');
		var end = document.getElementById('end');
		var complete = document.getElementById('complete');
		var j = 0;
		stop = true;
		end.style.display = "none";
		start.style.display = "none";
		complete.style.display = "inline";
		complete.setAttribute("disabled", "disabled");
		setTimeout(() => {
			// 依次添加到右侧
			setTimeout(each, 1000);

			function each() {
				console.log(a);
				a.push(lucky[j]);
				console.log(1111);
				if (j < arr.length) {
					ennt.innerHTML = "";
					j++;
					// debugger;
					a.forEach(function (item) {
						// if(item.avatar ==undefined){
						// 	item.avatar
						// // }
						// console.log(item);
						// console.log('sdjnfwself');
						ennt.innerHTML += "<li id='in'>" +
							"<img src='" + item.avatar + "'/>" +
							// "<div class='text'>" +
							"<p > " + item.userName + "</p>" +
							"<p id='companyname'> " + item.companyName + "</p>" +
							"<p id='tel'> " + item.userPhone + "</p>" +
							"</li>";
					})
					setTimeout(each, 1000);
				}
				if (j === arr.length) {
					finish = true;
					complete.removeAttribute("disabled");
					$('#ul').css("margin-top", "0");
                    //获取最后一个目标li
                    var aim = $("#cnnt").find("li").eq(arr.length-1);
                    var company = aim.find("p").eq(1).text();
                    var name = aim.find("p").eq(0).text();
                    var phone = aim.find("p").eq(2).text();
                    var url = aim.find("img").attr("src");
                    $('#company').text(company);
                    $('#name').text(name);
                    $('#phone').text(phone);
                    $("#ul").find("li").eq(0).find("img").attr("src",url);
				}
			}
		}, 500)

	}
	document.getElementById("confirm").onclick = function () {
		document.getElementById("mask").style.display = "none";
	}
	// 点击取消重新加载页面
	document.getElementById("cancel").onclick = function () {
		window.location.reload();

	}
	// 点击提交
	document.getElementById('confirm').onclick = function () {
		// debugger;
		console.log(lucky);
		var arrly = '';
		// debugger;
		for (var c = 0; c < lucky.length; c++) {
			var a = lucky[c].userId;
			arrly += a + ",";
		}
		arrly = arrly.substring(0, arrly.length - 1);
		console.log(arrly);
		$.ajax({
			type: "POST",
			url: "../../api/web/app/draw/insertWinDraw",
			dataType: 'json',
			data: {
				actvid: activeid,
				meetingid: meet,
				prizeId: jiangxiang,
				userId: arrly
			},
			success: function (res) {
				console.log(res);
				console.log(this.data);
			},
			error: function (err) {
				console.log(err);
			}
		})
		$('#mask').hide();
		console.log(activeid);
		console.log(meet);

		window.location.href = "index.html?actvid=" + activeid + '&meetingid=' + meet
	}

	//开始抽奖
	function startRaffle() {
		// huoquneiding(zhongjiang);
		console.log("lucky---------------")
		console.log(lucky)
		lucky = [];
		var m = 0;
		s = setTimeout(lun, 1000);
		company = document.getElementById('company');
		na = document.getElementById('name');
		phone = document.getElementById('phone');
		getnum();

		function lun() {
			m += 7;
			document.getElementById('ul').style.marginTop = -m + "px";
			if (m % 140 == 0) {
				i = m / 140 - 1;
				company.innerText = list[i].companyName;
				na.innerText = list[i].userName;
				phone.innerText = list[i].userPhone;

				if (!finish) {
					setTimeout(lun, 1);
				} else {
					company.innerText = lucky[lucky.length - 1].companyName;
					na.innerText = lucky[lucky.length - 1].userName;
					phone.innerText = lucky[lucky.length - 1].userPhone;
				}
			} else if (m >= 140 * (list.length - 1)) {
				m = 0;
				s = setTimeout(lun, 1);
			} else {
				s = setTimeout(lun, 1);
			}
		}
	}
}