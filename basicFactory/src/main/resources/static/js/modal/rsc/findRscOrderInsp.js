$(document).ready(function(){
  //조회버튼 모달 팝업
  $("#outTable").on("click", ".rscOrderCode", function (e) {
   e.preventDefault();
   findRscOrderInspList();
   $("#findRscOrderInspModal").modal("show");
 
  })

   //발주목록 리스트 출력
 function findRscOrderInspList() {
  let rscOrderCd = $("#rscOrderCd").val();
  let rscOrderTtl = $("#rscOrderTtl").val();
  let rscOrderDt = $("#rscOrderDt").val();

  $.ajax({
   url: "findRscOrderInsp",
   method: "GET",
   contentType: "application/json;charset=utf-8",
   dataType: "json",
   data: {
    rscOrderCode: rscOrderCd,
    rscOrderTitle: rscOrderTtl,
    rscOrderDate: rscOrderDt
   },
   error: function (error, status, msg) {
    alert("상태코드 " + status + "에러메시지" + msg);
   },
   success: function (data) {
    $("#findRscOrderInsptbody tr").remove();
    let index = 0;
    for (obj of data) {
     index += 1;
     makeRscOrderInspRow(obj, index);
    }
   },
  });
 }

  //발주목록 행생성
  function makeRscOrderInspRow(obj, index) {
   let node = `
   <tr>
             <td>${obj.rscOrderCode}</td>
             <td>${obj.rscOrderDate}</td>
             <td>${obj.rscOrderTitle}</td>
           </tr>`;
   $("#findRscOrderInsptbody").append(node);
  }
 

 //검색버튼
 $("#searchOut").click(function (){
  findRscOrderInspList();
 })


 //테이블 클릭이벤트
 $("#findRscOrderInsptbody").on("click", "tr", function () {
  let rscOrderCode = $(this).find("td:eq(0)").text();
  let rscOrderDate = $(this).find("td:eq(1)").text();
  let rscOrderTitle = $(this).find("td:eq(2)").text();
  $("#rscOrderCode").val(rscOrderCode);
//여기까지 수정완료

  
  //발주목록 상세 내역 불러오기
  $.ajax({
   url: "inspListLoad",
   method: "GET",
   contentType: "application/json;charset=utf-8",
   dataType: "json",
   data: {
    rscOrderCode: rscOrderCode,
    rscOrderDate: rscOrderDate,
    rscOrderTitle: rscOrderTitle
   },
   error: function (error, status, msg) {
    alert("상태코드 " + status + "에러메시지" + msg);
   },
   success: function (data) {
    if(data.length == 0){
      return;
    }
     let trSize = $("#outTable tr").length - 1;
     $("#outTable tr").eq(trSize).remove();
     for (obj of data) {
      outListInsert(obj);
     }
     console.log(data)
   },
  });

  $("#findRscOrderInspModal").modal("hide");
 });

 
 function outListInsert(obj) {
  let node = `<tr>
  <td><input type="checkbox" name="chk"></td>
  <td><input type="text" class="rscOrderCode" value="${obj.rscOrderCode}" disabled></td>
  <td><input type="text" class="rscInspCode" disabled></td>
  <td><input type="date" class="rscInspDate"></td>
  <td><input type="text" class="rsccode" value="${obj.rscCdCode}" disabled></td>
  <td><input type="text" class="rscname" value="${obj.rscCdName}"disabled></td>
  <td><input type="text" class="unit" value="${obj.rscCdUnit}" disabled></td>
  <td><input type="text" class="inspVol" value="${obj.rscOrderVol}"></td>
  <td><input type="text" class="inferVol"></td>
  <td><input type="text" class="passVol" disabled></td>
  <td><input type="text" class="empId"></td>
  <td><input type="text" class="remk"></td>
  <input type="hidden" class="rscOrderDtlNo" value="${obj.rscOrderDtlNo}">
  <input type="hidden" class="rscOrderVol" value="${obj.rscOrderVol}">
  </tr>`;
  $("#InsertTable tbody").append(node);
 }


 function overlapWarning() {
  Swal.fire({
   icon: "warning", // Alert 타입
   title: "중복된 항목이 있습니다.", // Alert 제목
   confirmButtonText: "확인",
  })
 }

})