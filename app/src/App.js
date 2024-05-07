import React from 'react';
import BeerList from './BeerList';

function App() {
  return (
      <div>
        <header>
          <h1>BEerConnect</h1>
        </header>
        <nav>
          {/* Add navigation links */}
        </nav>
        <section>
          <center>
            <p>Welcome to BEerConnect, a place where you can discover new Pub, new Beer and new mates</p>
          </center>
        </section>
        <section id="beerList">
          <BeerList />
        </section>
        {/* Add more sections for other parts of your application */}
      </div>
  );
}

export default App;
