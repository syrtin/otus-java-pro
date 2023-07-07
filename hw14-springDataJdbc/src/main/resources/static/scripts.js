function postNewClient() {
    const clientNameContainer = document.getElementById('clientNameContainer');
    const clientAddressContainer = document.getElementById('clientAddressContainer');
    const clientPhonesContainer = document.getElementById('clientPhonesContainer');
    const name = clientNameContainer.value;

    const address = {
        street: clientAddressContainer.value
    };

    const phoneNumbersString = clientPhonesContainer.value;
    const phoneArray = phoneNumbersString.split(',');
    const phoneNumbersWithoutSpaces = phoneArray.map(phone => phone.trim());
    const phones = phoneNumbersWithoutSpaces.map(phoneNumber => ({phoneNumber: phoneNumber}));

    const client = {
        name: name,
        address: address,
        phones: phones
    }

    fetch('/api/client', {
        method: "post",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)
    })
        .then(response => {
            location.reload();
        })
}

document.addEventListener("DOMContentLoaded", function () {
    fetch('/api/client')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#clientTable tbody');

            data.forEach(client => {
                const {id, name, address, phones} = client;

                const row = document.createElement('tr');

                const idCell = document.createElement('td');
                idCell.textContent = id;
                row.appendChild(idCell);

                const nameCell = document.createElement('td');
                nameCell.textContent = name;
                row.appendChild(nameCell);

                const addressCell = document.createElement('td');
                addressCell.textContent = address.street;
                row.appendChild(addressCell);

                const phonesCell = document.createElement('td');
                phones.forEach(phone => {
                    const phoneSpan = document.createElement('span');
                    phoneSpan.textContent = phone.phoneNumber;
                    phonesCell.appendChild(phoneSpan);

                    const br = document.createElement('br');
                    phonesCell.appendChild(br);
                });
                row.appendChild(phonesCell);

                // Appending row to the table body
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
});