package com.zOldtheconnect;

import java.util.Arrays;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Friends {

@Id
@Index
private String userIds[];

public String[] getUserIds() {
	return userIds;
}

public void setUserIds(String[] userIds) {
	this.userIds = userIds;
}

@Override
public String toString() {
	return "Friends [userIds=" + Arrays.toString(userIds) + "]";
}


}
