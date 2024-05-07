// BeerList.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';

function BeerList() {
    const [beers, setBeers] = useState([]);

    useEffect(() => {
        axios.get('/beerlist')
            .then(response => {
                setBeers(response.data);
            })
            .catch(error => {
                console.error('Error fetching beers:', error);
            });
    }, []);

    return (
        <div>
            <h2>Beer List</h2>
            <ul>
                {beers.map(beer => (
                    <li key={beer.id}>
                        <div>Name: {beer.name}</div>
                        <div>Type: {beer.type}</div>
                        {/* Add more details if needed */}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default BeerList;
