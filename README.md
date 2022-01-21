First pet project: Github client copy
=====================================

desired features:
=================
* oauth authorization
* offline mode
* profile
* home page
* review repositories
* review author
* settings
* themes and customisation

Plan:
=====
* Model
    + Github service
        - oauth authorization
        - get methods
        - review repository (file tree)
        - Data base
            - user copy
            - his repository (without review, only about)
        - Network
            - get methods
            - oauth authorization
    + Data
        - user
        - author
        - repository
        - organisation
        - ...

* View
    + Navigation
        - profile screen
        - home screen
        - review repository screen
        - settings screen
    + Components
        - (no idea)
    + Themes and customisation
    + Settings

* ViewModel
    + profile
    + browsed repository
    + get info (about, organisations, stared, ...)
    + settings/config