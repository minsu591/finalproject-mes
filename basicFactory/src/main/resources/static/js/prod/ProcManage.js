$(document).ready(function () {
  findProcManage();

  $("#workInsertTable").on("click", "button", function () {
    console.log($(this).parent().parent().find("td:eq(2)").text());
    $("#workInsertModal").modal("show");
  });

  let fltyCnt = 0;
  $("#fltyCnt").val(fltyCnt);
  //불량증가
  $("#fltyUp").click(function () {
    $("#fltyCnt").val((fltyCnt += 1));
  });

  //불량감소
  $("#fltyDown").click(function () {
    if (fltyCnt == 0) {
      $("#fltyCnt").val(0);
    } else {
      $("#fltyCnt").val((fltyCnt -= 1));
    }
  });

  //작업시작시간 입력
  $("#workStartBtn").click(function () {
    var date = new Date();
    var hours = date.getHours();
    var minutes = date.getMinutes();
    $("#sHours").val(hours).prop("readonly", true);
    $("#sMinutes").val(minutes).prop("readonly", true);
  });

  $("#workEndBtn").click(function () {
    var date = new Date();
    var hours = date.getHours();
    var minutes = date.getMinutes();
    $("#eHours").val(hours).prop("readonly", true);
    $("#eMinutes").val(minutes).prop("readonly", true);
  });

  $("#procManageTable").on("click", "tr", function () {
    if ($(this).find("td:eq(0)").children().prop("checked")) {
      let prodName = $(this).find("td:eq(5)").text();
      $("#workStateTable thead tr td").text(prodName);
      console.log("제품명->" + prodName);

      $.ajax({
        url: `findprocess`,
        method: "GET",
        dataType: "json",
        success: function (data) {
          console.log(data);
          $("#workInsertTable tbody tr").remove();
          let index = 0;
          for (obj of data) {
            index += 1;
            workinsertTableMakeRow(obj);
          }
        },
      });
    } else {
      console.log("unchecked");
    }
  });
});

function findProcManage() {
  $.ajax({
    url: `findprocmanage`,
    method: "GET",
    dataType: "json",
    success: function (data) {
      console.log(data);
      $("#procManageTable tbody tr").remove();
      let index = 0;
      for (obj of data) {
        index += 1;
        procManageMakeRow(obj, index);
      }
    },
  });
}

function procManageMakeRow(obj, index) {
  let node = `<tr>
                <td><input type="checkbox"></td>
                <td>${index}</td>
                <td>${obj.workDate}</td>
                <td>${obj.instNo}</td>
                <td>${obj.finPrdCdCode}</td>
                <td>${obj.finPrdCdName}</td>
                <td>${obj.instProdIndicaVol}</td>
                <td>${obj.virResult}</td>
                <td>${obj.nonResult}</td>
                <td>${obj.workScope}</td>
              </tr>`;

  $("#procManageTable tbody").append(node);
}

function workinsertTableMakeRow(obj) {
  // [{
  //   "processNo": 23,
  //   "instProdNo": 3,
  //   "processOrder": 1,
  //   "procCdCode": "PROC001",
  //   "mchnCode": "MCHN001",
  //   "inDtlVol": 0,
  //   "totalProdVol": 0,
  //   "fltyVol": 0,
  //   "completionStatus": "n",
  //   "processRemk": null,
  //   "virResult": 0,
  //   "nonResult": 0
  // },
  let node = `<tr> 
              <td>${obj.processOrder}</td>
              <td>${obj.procCdName}</td>
              <td>${obj.mchnName}</td>
              <td></td>
              <td>${obj.inDtlVol}</td>
              <td>${obj.virResult}</td>
              <td>${obj.nonResult}</td>
              <td>${obj.fltyVol}</td>
              <td><button type="submit" class="btn  btn-primary">진행전</button></td> 
              </tr>`;

  $("#workInsertTable tbody").append(node);
}
