// 거래 내역 데이터를 가져와서 차트를 생성하는 함수
function renderExpenseChart() {
    fetch('/transactions')
        .then(response => response.json())
        .then(transactions => {
            // 카테고리별 지출 합계 계산
            const expenseData = {};
            transactions.forEach(transaction => {
                const category = transaction.category || '기타';
                const amount = parseFloat(transaction.amount);
                if (expenseData[category]) {
                    expenseData[category] += amount;
                } else {
                    expenseData[category] = amount;
                }
            });

            // 차트를 렌더링할 캔버스 요소
            const ctx = document.getElementById('expenseChart').getContext('2d');
            new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: Object.keys(expenseData),
                    datasets: [{
                        data: Object.values(expenseData),
                        backgroundColor: [
                            '#FF6384',
                            '#36A2EB',
                            '#FFCE56',
                            '#4BC0C0',
                            '#9966FF',
                            '#FF9F40'
                        ]
                    }]
                },
                options: {
                    responsive: true,
                    title: {
                        display: true,
                        text: '카테고리별 지출 비율'
                    }
                }
            });
        });
}

// 페이지 로드 시 차트를 렌더링
document.addEventListener('DOMContentLoaded', renderExpenseChart);
