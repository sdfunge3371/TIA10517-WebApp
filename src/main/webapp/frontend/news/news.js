const products = [
           { id: 1, title: '最新優惠', content: '優惠優惠優惠優惠優惠' },
           { id: 2, title: '裝潢公告', content: '公告公告公告公告公告' },
           { id: 3, title: '優惠券發放', content: '發放發放發放發放發放' },
           // 添加更多商品資料
           { id: 4, title: '徵才消息', content: '消息消息消息消息消息' },
           { id: 5, title: '春節優惠', content: '春節春節春節春節春節' },
           { id: 6, title: '最新優惠2', content: '優惠優惠優惠優惠優惠222' },
           { id: 7, title: '裝潢公告2', content: '公告公告公告公告公告222' },
           { id: 8, title: '優惠券發放2', content: '發放發放發放發放發放222' },
           { id: 9, title: '徵才消息2', content: '消息消息消息消息消息222' },
           { id: 10, title: '春節優惠2', content: '優惠優惠優惠優惠優惠2222' },
           { id: 11, title: '最新優惠3', content: '優惠優惠優惠優惠優惠333' },
           { id: 12, title: '裝潢公告3', content: '公告公告公告公告公告333' },
           { id: 13, title: '優惠券發放3', content: '公告公告公告公告公告333' },
           { id: 14, title: '徵才消息3', content: '公告公告公告公告公告333' }
       ];

       const itemsPerPage = 5; // 每頁顯示的商品數量
       let currentPage = 1;

       function displayProducts(page) {
           const startIndex = (page - 1) * itemsPerPage;
           const endIndex = startIndex + itemsPerPage;
           const pageProducts = products.slice(startIndex, endIndex);

           const tableBody = document.getElementById('productTableBody');
           tableBody.innerHTML = ''; // 清空表格

           pageProducts.forEach(product => {
               const row = document.createElement('tr');
               row.innerHTML = `
           <td>${product.id}</td>
           <td>${product.title}</td>
           <td>${product.content}</td>
       `;
               tableBody.appendChild(row);
           });
       }

       function displayPagination() {
           const totalPages = Math.ceil(products.length / itemsPerPage);
           const pagination = document.getElementById('pagination');
           pagination.innerHTML = ''; // 清空分頁

           for (let i = 1; i <= totalPages; i++) {
               const listItem = document.createElement('li');
               listItem.classList.add('page-item');
               if (i === currentPage) {
                   listItem.classList.add('active');
               }

               const link = document.createElement('a');
               link.classList.add('page-link');
               link.href = '#';
               link.textContent = i;
               link.addEventListener('click', () => {
                   currentPage = i;
                   displayProducts(currentPage);
                   displayPagination();
               });

               listItem.appendChild(link);
               pagination.appendChild(listItem);
           }
       }

       // 初始載入
       displayProducts(currentPage);
       displayPagination();