import React, {Component} from 'react';

import {StackNavigator} from 'react-navigation'
import Home from "./Home.js";
import BookList from "./BookList.js";
import Details from "./Details";
import EmailPage from "./EmailPage.js"

global.books=[
    {
        key:'1',
        title:'1984',
        author:'George Owell',
        genre:'politics',
        exchangeMethod:'Borrow',
        location:'Cluj-Napoca'
    },
    {
        key:'2',
        title:'The Great Gabsy',
        author:'F. Scott Fitzgerald',
        genre:'fiction, history',
        exchangeMethod:'Sell 4$',
        location:'Cluj-Napoca'
    },
    {
        key:'3',
        title:'Gone With The Wind',
        author:'Margaret Mitchell',
        genre:'history, fiction',
        exchangeMethod:'Donation',
        location:'Dej'
    },
    {
        key:'4',
        title:'Jane Eyre',
        author:'Charlotte Bronte',
        genre:'autobiography, romance',
        exchangeMethod:'Borrow',
        location:'Cluj-Napoca'
    },
]


const NavigationApp=StackNavigator({
    Home:{screen:Home},
    BookList:{screen:BookList},
    Details:{screen:Details},
    EmailPage:{screen:EmailPage}
});


export default class App extends React.Component {
  render() {
    return (
      <NavigationApp/>
    );
  }
}

