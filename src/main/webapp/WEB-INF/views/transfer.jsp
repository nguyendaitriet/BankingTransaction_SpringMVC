<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Transfer</title>
    <%@ include file="/layout/head.jsp"%>
</head>

<body>
    <div class="container">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-5">
                    <h1>Transfer</h1>
                </div>
                <div class="col-sm-7">
                    <a href="./" class="btn btn-outline-light">
                        <i class="fa-solid fa-bars"></i>
                        <span>List of customers</span>
                    </a>
                </div>
            </div>
        </div>
        <div>
            <form method="post" autocomplete="off">
                <div class="row mt-3">
                    <div class="mb-3 float-left col-lg-3 col-sm-12 col-md-6">
                        <label for="senderID" class="form-label">Sender ID</label>
                        <input type="number" class="form-control" id="senderID"
                               value="${sender.getId()}" readonly
                        >
                    </div>
                    <div class="mb-3 float-left col-lg-3 col-sm-12 col-md-6">
                        <label for="senderName" class="form-label">Sender Name</label>
                        <input type="text" class="form-control" id="senderName"
                               value="${sender.getFullName()}" readonly
                        >
                    </div>
                    <div class="mb-3 float-left col-lg-3 col-sm-12 col-md-6">
                        <label for="email" class="form-label">Sender Email</label>
                        <input type="email" class="form-control" id="email"
                               value="${sender.getEmail()}" readonly
                        >
                    </div>
                    <div class="mb-3 float-left col-lg-3 col-sm-12 col-md-6">
                        <label for="balance" class="form-label">Sender Balance</label>
                        <input type="number" class="form-control" id="balance"
                               value="${sender.getBalance()}" readonly
                        >
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="mb-3 float-left col-lg-3 col-sm-12 col-md-6">
                        <label for="recipientName" class="form-label">Recipient Name</label>
                        <select id="recipientName" name="recipientId" class="form-control">
                            <c:forEach var="recipient" items="${recipients}">
                                <option value="${recipient.getId()}">(${recipient.getId()}) ${recipient.getFullName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3 float-left col-lg-3 col-sm-12 col-md-6">
                        <label for="transfer" class="form-label">Transfer Amount (VND)</label>
                        <input type="number" class="form-control" id="transfer" name="transfer" value="0">
                    </div>
                    <div class="mb-3 float-left col-lg-3 col-sm-12 col-md-6">
                        <label for="fees" class="form-label">Fee (%)</label>
                        <input type="number" class="form-control" id="fees" value="10" readonly>
                    </div>
                    <div class="mb-3 float-left col-lg-3 col-sm-12 col-md-6">
                        <label for="total" class="form-label">Total Amount (VND)</label>
                        <input type="number" class="form-control" id="total" value="0" readonly>
                    </div>
                </div>
                <button type="submit" class="btn btn-outline-warning">
                    <i class="fas fa-exchange-alt"></i>
                    Transfer
                </button>
            </form>
        </div>
    </div>
    <c:choose>
        <c:when test="${transferred}">
            <div class="fixed-bottom alert alert-success alert-dismissible">
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                <strong>${message}</strong>
            </div>
        </c:when>
        <c:when test="${transferred == false}">
            <div class="fixed-bottom alert alert-danger alert-dismissible">
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                <strong>${message}</strong>
            </div>
        </c:when>
    </c:choose>
    <%@ include file="/layout/script.jsp"%>
    <script>
        document.addEventListener("input", () =>{
            let fee = Number(document.getElementById("fees").value);
            let transferAmount = Number(document.getElementById("transfer").value);
            let transactionFee =  transferAmount * fee / 100;
            document.getElementById("total").value = transactionFee + transferAmount;
        })
    </script>
</body>

</html>