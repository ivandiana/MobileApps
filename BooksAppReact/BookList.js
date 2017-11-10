import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    FlatList,
} from 'react-native';

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

    render(){
        const{navigate}=this.props.navigation;
        return(
            <View style={styles.container}>
                <View style={styles.header}>
                    <Text style={styles.headerText}>Available Books</Text>
                </View>

                <FlatList
                    data={books}
                    renderItem={({item})=>
                        <ScrollView>
                            <View style={styles.linearView}>
                                <Text style={styles.item} onPress={()=>navigate('Details',{book:item})
                                }>{item.title}</Text>
                            </View>
                        </ScrollView>
                    }
                />
            </View>
        );
    }
}