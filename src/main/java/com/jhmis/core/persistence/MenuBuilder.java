package com.jhmis.core.persistence;

import java.util.ArrayList;
import java.util.List;

public class MenuBuilder {
    /**
     * 两层循环实现建树
     * @param MenuNodes 传入的树节点列表
     * @return
     */
    public static List<MenuNode> bulid(List<MenuNode> MenuNodes) {

        List<MenuNode> trees = new ArrayList<>();

        for (MenuNode menuNode : MenuNodes) {

            if (menuNode.getRootId().equals(menuNode.getParentId())) {
                trees.add(menuNode);
            }

            for (MenuNode it : MenuNodes) {
                if (it.getParentId().equals(menuNode.getId())) {
                    if (menuNode.getChildren() == null) {
                        menuNode.setChildren(new ArrayList<MenuNode>());
                    }
                    menuNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     * @param MenuNodes
     * @return
     */
    public static List<MenuNode> buildByRecursive(List<MenuNode> MenuNodes) {
        List<MenuNode> trees = new ArrayList<MenuNode>();
        for (MenuNode menuNode : MenuNodes) {
            if (menuNode.getRootId().equals(menuNode.getParentId())) {
                trees.add(findChildren(menuNode,MenuNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param MenuNodes
     * @return
     */
    public static MenuNode findChildren(MenuNode MenuNode,List<MenuNode> MenuNodes) {
        for (MenuNode it : MenuNodes) {
            if(MenuNode.getId().equals(it.getParentId())) {
                if (MenuNode.getChildren() == null) {
                    MenuNode.setChildren(new ArrayList<MenuNode>());
                }
                MenuNode.getChildren().add(findChildren(it,MenuNodes));
            }
        }
        return MenuNode;
    }



    public static void main(String[] args) {

        MenuNode MenuNode1 = new MenuNode("1","广州","0");
        MenuNode MenuNode2 = new MenuNode("2","深圳","0");

        MenuNode MenuNode3 = new MenuNode("3","天河区",MenuNode1);
        MenuNode MenuNode4 = new MenuNode("4","越秀区",MenuNode1);
        MenuNode MenuNode5 = new MenuNode("5","黄埔区",MenuNode1);
        MenuNode MenuNode6 = new MenuNode("6","石牌",MenuNode3);
        MenuNode MenuNode7 = new MenuNode("7","百脑汇",MenuNode6);


        MenuNode MenuNode8 = new MenuNode("8","南山区",MenuNode2);
        MenuNode MenuNode9 = new MenuNode("9","宝安区",MenuNode2);
        MenuNode MenuNode10 = new MenuNode("10","科技园",MenuNode8);


        List<MenuNode> list = new ArrayList<MenuNode>();

        list.add(MenuNode1);
        list.add(MenuNode2);
        list.add(MenuNode3);
        list.add(MenuNode4);
        list.add(MenuNode5);
        list.add(MenuNode6);
        list.add(MenuNode7);
        list.add(MenuNode8);
        list.add(MenuNode9);
        list.add(MenuNode10);

        List<MenuNode> trees = MenuBuilder.bulid(list);

        List<MenuNode> trees_ = MenuBuilder.buildByRecursive(list);

    }
}
