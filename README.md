Client-side mod to allow building as if you had a "Builder's Wand" from [insert mod here]

***

Decompilation of "Client Builders Wand" by waechter_von_atlantis (licensed under the MIT License)

https://www.curseforge.com/minecraft/mc-mods/client-builders-wand

***

TODO:
- Bugs
  - Disabling the "Replace plants" setting in-game doesn't reflect the change in the settings menu until it's closed + re-opened (???)  
    (bug existed in original version)

- Updates
  - Refactor config implementation (Cloth Config?)
  - Port to Architectury (including Fabric and Quilt)
  - Port to >1.19.2

- Features
  - Max. number of blocks to place per right-click
  - Only place blocks on connected blocks (no building on blocks not (indirectly) attached to the initial block)
  - Restrict placement to specific axis (horizontal, vertical, either, etc.)
