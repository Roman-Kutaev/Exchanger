const baseUrlReport = "http://localhost:8080/api/v1/exchanger/request/report";
const baseUrlRequest = "http://localhost:8080/api/v1/exchanger/request";

document.querySelector("#showReport").addEventListener('click', event => {
    let startDate = document.querySelector('#startDate');
    let endDate = document.querySelector('#endDate');
    let currency = document.querySelector('#currency');
    console.log('startDate = ' + startDate);
    fetch(baseUrlReport + "/" + startDate.value + "/" + endDate.value + "/" + currency.value, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
        .then(json => {
            startDate.value = endDate.value = currency.value = '';
            show(json);
        });
})

function show(reportArr) {
    console.log('Report = ' + reportArr);
    const tbody = document.querySelector('#bodyReport');
    tbody.innerHTML = '';
    reportArr.forEach(report => {
        let tr = document.createElement('tr');
        for (const prop in report) {
            let td = document.createElement('td');
            td.append(document.createTextNode(report[prop]));
            tr.append(td);
        }
        tbody.append(tr);
    });
}

document.querySelector("#showReportByDay")
    .addEventListener('click', evt => {
        fetch(baseUrlRequest + "/shutDownContext"
            , {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        ).finally(function (){
            alert("Приложение завершило свою работу")
        })
    })

document.querySelector("#showReportByDay")
    .addEventListener('click', evt => {
        fetch(baseUrlReport)
            .then(response => response.json())
            .then(json => {
                show(json);
            });
    })

document.querySelector("#deleteRequest")
    .addEventListener('click', evt => {
        let phone = document.querySelector("#phoneNumber")
        console.log('phone = ' + phone.value)
        fetch(baseUrlRequest + "/delete/" + phone.value,
            {
                method: 'DELETE',
                headers:{
                    'Content-Type': 'application/json'
                }
            } ).then(function (response) {
                phone.value = '';
            if (response.status === 200) {
               alert('Заявка удалена.')
            }
            else if (response.status === 204){
                alert('Статус заявки не "Новая".')
            } else{
                alert('По данному номеру телефона заявку не найдено.')
            }
        })
    })

const dialog = document.querySelector('dialog');
document.querySelector("#showReportByDay").onclick =
    document.querySelector("#showReport").onclick = function () {
        dialog.showModal();
    }

document.querySelector("#close").onclick = function () {
    dialog.close();
}
