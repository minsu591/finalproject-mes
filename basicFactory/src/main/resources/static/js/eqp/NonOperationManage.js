$(document).ready(function () {
  findAllProcCode();
  findMchnName();

  //작업 종료 버튼
  $("#workEndBtn").click(function () {
    let date = new Date();
    let hours = date.getHours();
    let minutes = date.getMinutes();
    $("#eHours").val(hours).prop("readonly", true);
    $("#eMinutes").val(minutes).prop("readonly", true);

    let mchnCode = $("#mchnCode").val();
    $.ajax({
      url: `endmchnstatusupdate/${mchnCode}`,
      method: "POST",
      contentType: "application/json;charset=utf-8",
      dataType: "json",
      error: function (error, status, msg) {
        alert("상태코드 " + status + "에러메시지" + msg);
      },
      success: function (data) {
        // console.log(data);
      },
    });
  });
  //작업시작시간 입력
  $("#workStartBtn").click(function () {
    let mchnCode = $("#mchnCode").val();
    let mchnStatus = $("#mchnStatus").val();

    if (mchnStatus == "비가동") {
      alert("비가동중입니다.");
    } else if (mchnCode == "") {
      alert("설비를 선택하세요");
    } else {
      findMchnName();
      let date = new Date();

      let hours = date.getHours();
      let minutes = date.getMinutes();
      $("#sHours").val(hours).prop("readonly", true);
      $("#sMinutes").val(minutes).prop("readonly", true);
      $.ajax({
        url: `startmchnstatusupdate/${mchnCode}`,
        method: "POST",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        error: function (error, status, msg) {
          alert("상태코드 " + status + "에러메시지" + msg);
        },
        success: function (data) {},
      });
      $.ajax({
        url: `findinputno`,
        method: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        error: function (error, status, msg) {
          alert("상태코드 " + status + "에러메시지" + msg);
        },
        success: function (data) {
          // console.log("inputno-> " + data);
          $("#inputNo").val(data);
        },
      });

      nonOpTableMakeRow();
    }
  });
  //설비테이블 클릭 이벤트
  $("#equipTable").on("click", "tr", function () {
    let mchnCode = $(this).find("td:eq(0)").text();
    let mchnName = $(this).find("td:eq(1)").text();
    let mchnStatus = $(this).find("td:eq(3)").text();
    $("#mchnCode").val(mchnCode).prop("readonly", true);
    $("#mchnName").val(mchnName).prop("readonly", true);
    $("#mchnStatus").val(mchnStatus);
  });

  //공정셀렉티드 검색
  $("#selectProcCdName").bind("input", function () {
    let procCdName = $("#selectProcCdName option:selected").text();
    $.ajax({
      url: `findmchn/${procCdName}`,
      method: "GET",
      contentType: "application/json;charset=utf-8",
      dataType: "json",
      error: function (error, status, msg) {
        alert("상태코드 " + status + "에러메시지" + msg);
      },
      success: function (data) {
        $("#equiptbody tr").remove();
        for (obj of data) {
          mchnMakeRow(obj);
        }
      },
    });
  });

  //비가동테이블 ㅡㄹ릭 이벤트
  $("#nonOpTable").on("click", "tr", function () {
    let nonOpCode = $(this).find("td:eq(0)").children();
    let nonOpName = $(this).find("td:eq(1)").children();
    console.log(nonOpName);
    nonOpCode.bind("input", function () {
      console.log($(this).val());
      let nonOpCode = $(this).val();
      $.ajax({
        url: "findnonop",
        method: "GET",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: {
          nonOpCode: nonOpCode,
          nonOpName: null,
        },
        error: function (error, status, msg) {
          nonOpName.val("");
        },
        success: function (data) {
          console.log(data);
          if (data.length == 0) {
            nonOpName.val("");
          } else {
            for (obj of data) {
              console.log(obj.nonOpName);
              nonOpName.val(obj.nonOpName);
            }
          }
        },
      });
    });
  });

  $("#saveBtn").click(function () {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    console.log(year + "-" + month + "-" + day);
    $("#inputNo").val(); //입력번호
    $("#mchnCode").val(); //설비코드
    console.log($("#nonOpTable tbody tr").find("td:eq(0)").children().val()); //비가동코드
    $("#empid").val(); //작업자
    $("inputDate").val(); //입력일자
    $("#sHours").val(); //시작시간
    $("#sMinutes").val(); //시작분
    $("#eHours").val(); //종료시간
    $("#eMinutes").val(); //종료분
    console.log($("#nonOpTable tbody tr").find("td:eq(2)").children().val()); //작업내용
    console.log($("#nonOpTable tbody tr").find("td:eq(3)").children().val()); //비고
  });
});

//설비테이블
function findMchnName() {
  $.ajax({
    url: "findmchn",
    method: "GET",
    contentType: "application/json;charset=utf-8",
    dataType: "json",
    error: function (error, status, msg) {
      alert("상태코드 " + status + "에러메시지" + msg);
    },
    success: function (data) {
      $("#equiptbody tr").remove();
      for (obj of data) {
        mchnMakeRow(obj);
      }
    },
  });
}

//공정검색
function findAllProcCode() {
  $.ajax({
    url: "findproccode",
    method: "GET",
    contentType: "application/json;charset=utf-8",
    dataType: "json",
    error: function (error, status, msg) {
      alert("상태코드 " + status + "에러메시지" + msg);
    },
    success: function (data) {
      console.log(data);
      let index = 0;
      for (obj of data) {
        console.log("어팬드");
        $("#selectProcCdName").append(
          "<option value='" + (index += 1) + "'>" + obj.procCdName + "</option>"
        );
      }
    },
  });
}

//설비테이블 행추가
function mchnMakeRow(obj) {
  let node = `<tr>                 
                   <td>${obj.mchnCode}</td>
                   <td>${obj.mchnName}</td>
                   <td>${obj.procCdName}</td>
                   <td>${obj.mchnStts}</td>
                  </tr>`;
  $("#equiptbody").append(node);
}
//비가동 입력 테이블 행 추가
function nonOpTableMakeRow() {
  let node = `<tr>
		            <td><input type="text"></td>
                <td><input type="text" readonly></td>
                <td><input type="text"></td>
                <td><input type="text"></td>
              </tr>`;

  $($("#nonOpTable tbody")).append(node);
}
