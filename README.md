# Knight Equipment Manager

## Description
**Knight Equipment Manager** is a Java console application for managing a knight’s equipment.  
The program supports three main item types:
- **Armor**
- **Weapons**
- **Accessories**

Users can load items from files, equip a knight, view inventory, sort items by weight or price, search items within a given price range, etc.  

---

# Class UML diagram
[UML Class Diagram (Boardmix)](https://boardmix.com/app/share/CAE.CLyitAEgASoQGjFo7YkfV3qifdXpywyLyDAGQAE/nP5QQI)

---

## Features
- 📂 **File handling**: load items from `.txt` files.  
- 🛡️ **Inventory management**: view all available items, equip and disarm the Knight.  
- ⚔️ **Knight equipment**: equip, disarm and view knight’s items.  
- ⚖️ **Sorting (both for inventory and knight’s equipment)**:
  - by weight;
  - by price.  
- 💰 **Search by price range**.  
- 🧩 **OOP principles**:
  - inheritance (`Armor`, `Weapon`, `Accessory` extend `KnightItem`);
  - encapsulation;
  - polymorphism.

---

## Project Structure
- `Knight` – manages knight’s equipment.  
- `Armor`, `Weapon`, `Accessory` – item classes extending `KnightItem`.
- `MaterialItem`- interface, implemented by the classes `Armor` and `Weapon`
- `FileHandler` – handles file input for items.  
- `ItemUtils` – utility class for sorting items.  
- `Menu` – console menu for user interaction.  
- `Main` – starts the program.  

---

## Features to add
**Functionalities** 
- The shop with items. The Knight can purchase, sell and trade items;
- Enemies the Knight can fight and defeat;
  
**Code enhancement**
- Automatic adding of the names of the types/materials to the inventoryArmorHierarchy() and inventoryMaterialHierarchy();
