import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    FlatList, Button, TextInput, ListView,AsyncStorage
} from 'react-native';
import PopupDialog from "react-native-popup-dialog/src/PopupDialog"

const styles= StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        backgroundColor: '#fff',
        paddingTop: 30,
        justifyContent: 'center',
    },
    header: {
        backgroundColor: 'white',
        alignItems: 'center',
        justifyContent: 'center',
        borderBottomWidth: 2,
        marginBottom: 3,
        borderBottomColor: '#71001a'
    },
    headerText: {
        color: '#71001a',
        fontSize: 30,
    },

    item: {
        fontSize: 15,
        justifyContent: 'center',
        marginBottom: 5,
    },
    linearView:{
        flexDirection:'row',}
})

export default class BookList extends Component{

    constructor(props) {
        super(props);
        this.state= {
            key:0,
            title:"",
            author:"",
            genre:"",
            exchangeMethod:"",
            location:"",
            dataSource:global.dataSource.cloneWithRows([]),
            allbooks:[],
        };

        this.selectedItem= {
        key:0,
        title:"",
        author:"",
        genre:"",
        exchangeMethod:"",
        location:""
        };
        this.persistBooks();
    }


    DeleteSelectedBook(){
        console.log("Delete book:"+String(this.selectedItem.key)+";"+this.selectedItem.title)
        var bookToDelete=this.selectedItem;
        AsyncStorage.removeItem(String(bookToDelete.key)).then(()=>this.persistBooks());
    }

    persistBooks(){
       AsyncStorage.getAllKeys().then((keys)=>{
           for(index in keys){
               this.state.allbooks=[];
               AsyncStorage.getItem(keys[index]).then((value)=>{
                    this.state.allbooks.push(JSON.parse(value));

                    this.setState({dataSource:global.dataSource.cloneWithRows(this.state.allbooks)});
               })
           }
       });
    }


    render(){
        const{navigate}=this.props.navigation;
        return(
            <View style={styles.container}>
                <View style={styles.header}>
                    <Text style={styles.headerText}>Available Books</Text>
                </View>

                <Button onPress={()=>this.props.navigation.navigate('AddBook',{persistBooks:this.persistBooks.bind(this),allbooks:this.state.allbooks})} title="Add book"/>

                <ListView
                    dataSource={this.state.dataSource}
                    renderRow={(item)=>
                            <View style={{flex:1,flexDirection:'row'}}>
                                <Text onPress={()=>{
                                this.selectedItem=item;
                                this.popupDialogMenu.show();
                                }
                                }>{item.title}</Text>
                            </View>}

                    />

                <PopupDialog ref={(popupDialog) => {
                    this.popupDialogMenu = popupDialog;
                }} >

                    <View>

                        <Button title="View" onPress={() => {this.popupDialogMenu.dismiss();navigate('ViewBook',{book:this.selectedItem})}}/>
                        <Button title="Edit" onPress={() => {this.popupDialogMenu.dismiss();navigate('Details',{book:this.selectedItem,persistBooks:this.persistBooks.bind(this)})}}/>
                        <Button title="Delete" onPress={() => {this.popupDialogMenu.dismiss();this.DeleteSelectedBook();}}/>
                    </View>
                </PopupDialog>
            </View>
        );
    }
}