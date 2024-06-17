# Some notes about endpoints and project structure

## Endpoints

### /api/v1/search

#### get /api/v1/search?text=
response:
```json
{
  "items": [],
  "categories": [],
  "typeQueries": []
}
```

#### get /api/v1/search?text=100001
100001 is a valid sku
response:
```json
{
  "items": [
    {
      "price": 1200,
      "name": "Laptop Model X",
      "url": "laptopmodelx.com",
      "image": "A123",
      "itemId": 101,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics BrandX"
    }
  ]
}
```

#### get /api/v1/search?text=SKU015
SKU015 is a valid sku with letters
returns empty items.

[//]: # (TODO bug)

```json
{
    "items": [],
    "categories": [],
    "typeQueries": []
}
```


#### get /api/v1/search?text=Laptop Model X
Laptop Model X is a valid name
```json
{
  "items": [
    {
      "price": 1200,
      "name": "Laptop Model X",
      "url": "laptopmodelx.com",
      "image": "A123",
      "itemId": 101,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics"
    }
  ]
}
```

#### get /api/v1/search?text=Wireless
Wireless is a part of name
```json
{
  "items": [
    {
      "price": 750,
      "name": "Wireless Earbuds",
      "url": "wirelessearbuds.com",
      "image": "N345",
      "itemId": 110,
      "cat": "Accessories"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Accessories"
    }
  ]
}
```

#### get /api/v1/search?text=Laptops

Laptops is a category
```json
{
  "items": [
    {
      "price": 1200,
      "name": "Laptop Model X",
      "url": "laptopmodelx.com",
      "image": "A123",
      "itemId": 101,
      "cat": "Electronics"
    },
    {
      "price": 20,
      "name": "Smartwatch Series Z",
      "url": "smartwatchz.com",
      "image": "K456",
      "itemId": 103,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics"
    }
  ]
}
```

#### get /api/v1/search?text=Electronics
Electronics is a valid type

```json
{
  "items": [
    {
      "price": 1200,
      "name": "Laptop Model X",
      "url": "laptopmodelx.com",
      "image": "A123",
      "itemId": 101,
      "cat": "Electronics"
    },
    {
      "price": 20,
      "name": "Smartwatch Series Z",
      "url": "smartwatchz.com",
      "image": "K456",
      "itemId": 103,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics"
    }
  ]
}
```

#### get /api/v1/search?text=Electronics [cookies regionId=2]
Electronics is a valid type and regionId is a valid cookie
```json
{
  "items": [
    {
      "price": 900,
      "name": "Tablet Pro",
      "url": "Tablet Pro",
      "image": "J123",
      "itemId": 102,
      "cat": "Electronics"
    },
    {
      "price": 150,
      "name": "Smartphone Y",
      "url": "smartphoney.com",
      "image": "B456",
      "itemId": 104,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics"
    }
  ]
}

```
#### get /api/v1/search?text=Electronics [cookies regionId=0]
Electronics is a valid type and regionId is an invalid cookie
```json
{
    "items": [],
    "categories": [],
    "typeQueries": [
        {
            "type": "SEE_ALSO",
            "text": "Electronics"
        }
    ]
}
```

#### get /api/v1/search?text=Laptp
Laptp is a typo
```json
{
  "items": [
    {
      "price": 1200,
      "name": "Laptop Model X",
      "url": "laptopmodelx.com",
      "image": "A123",
      "itemId": 101,
      "cat": "Electronics"
    },
    {
      "price": 20,
      "name": "Smartwatch Series Z",
      "url": "smartwatchz.com",
      "image": "K456",
      "itemId": 103,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics"
    }
  ]
}
```

#### get /api/v1/search?text=Laaptop
Laaptop is a typo
```json
{
  "items": [
    {
      "price": 1200,
      "name": "Laptop Model X",
      "url": "laptopmodelx.com",
      "image": "A123",
      "itemId": 101,
      "cat": "Electronics"
    },
    {
      "price": 20,
      "name": "Smartwatch Series Z",
      "url": "smartwatchz.com",
      "image": "K456",
      "itemId": 103,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics"
    }
  ]
}
```

#### get /api/v1/search?text=lptp
lptp is a typo
```json
{
  "items": [
    {
      "price": 1200,
      "name": "Laptop Model X",
      "url": "laptopmodelx.com",
      "image": "A123",
      "itemId": 101,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics"
    }
  ]
}
```

#### get /api/v1/search?text=что
что is a cyrillic word
```json
{
  "items": [],
  "categories": [],
  "typeQueries": []
}
```

#### get /api/v1/search?text=что-то
что-то is a cyrillic word with dash

[//]: # (TODO bug)
```json
{
  "items": [
    {
      "price": 1200,
      "name": "Laptop Model X",
      "url": "laptopmodelx.com",
      "image": "A123",
      "itemId": 101,
      "cat": "Electronics"
    },
    {
      "price": 20,
      "name": "Smartwatch Series Z",
      "url": "smartwatchz.com",
      "image": "K456",
      "itemId": 103,
      "cat": "Electronics"
    }
  ],
  "categories": [],
  "typeQueries": [
    {
      "type": "SEE_ALSO",
      "text": "Electronics"
    }
  ]
}
```

### /api/v1/search/by

#### get /api/v1/search/by?text=100001
100001 is a valid sku
```json
{
  "result": [
    {
      "name": "Laptops",
      "catalogueId": 1,
      "items": [
        {
          "name": "Laptop Model X",
          "itemId": 101,
          "brand": "BrandX",
          "type": "Electronics",
          "description": "high-performance laptop"
        }
      ],
      "brand": "BrandX"
    }
  ]
}
```

#### get /api/v1/search/by?text=100001 [cookies regionId=2]
100001 is a valid sku and regionId is a valid cookie
even if item is not in region id it will be returned

[//]: # (TODO bug)
```json
{
  "result": [
    {
      "name": "Laptops",
      "catalogueId": 1,
      "items": [
        {
          "name": "Laptop Model X",
          "itemId": 101,
          "brand": "BrandX",
          "type": "Electronics",
          "description": "high-performance laptop"
        }
      ],
      "brand": "BrandX"
    }
  ]
}
```

#### get /api/v1/search/by?text=Smartphone Y
Smartphone Y is a valid name
even if Smartphone Y is not in region id it will be returned

[//]: # (TODO bug)
```json
{
  "result": [
    {
      "name": "Smartphones",
      "catalogueId": 1,
      "items": [
        {
          "name": "Smartphone Y",
          "itemId": 104,
          "brand": "BrandY",
          "type": "Electronics",
          "description": "top-notch smartphone"
        }
      ]
    }
  ]
}
```

#### get /api/v1/search/by?text=Phone
Phone is a part of name
even if Phone is not in region id it will be returned

[//]: # (TODO bug)
```json
{
  "result": [
    {
      "name": "Accessories",
      "catalogueId": 7,
      "items": [
        {
          "name": "Phone Case",
          "itemId": 109,
          "brand": "BrandB",
          "type": "Accessories",
          "description": "protective phone case"
        }
      ]
    }
  ]
}
```

#### get /api/v1/search/by?text=Electronics
Electronics is a valid type

[//]: # (TODO bug)
```json
{
  "result": [
    {
      "name": "Smartphones",
      "catalogueId": 1,
      "items": [
        {
          "name": "Smartphone Y",
          "itemId": 104,
          "brand": "BrandY",
          "type": "Electronics",
          "description": "top-notch smartphone"
        }
      ]
    },
    {
      "name": "Laptops",
      "catalogueId": 1,
      "items": [
        {
          "name": "Tablet Pro",
          "itemId": 102,
          "brand": "BrandG",
          "type": "Electronics",
          "description": "advanced tablet for professionals"
        },
        {
          "name": "Smartwatch Series Z",
          "itemId": 103,
          "brand": "BrandH",
          "type": "Electronics",
          "description": "feature-rich smartwatch"
        },
        {
          "name": "Laptop Model X",
          "itemId": 101,
          "brand": "BrandX",
          "type": "Electronics",
          "description": "high-performance laptop"
        }
      ]
    }
  ]
}
```

#### get /api/v1/search/by?text=Electronics [cookies regionId=2]
Response not depends on regionId

[//]: # (TODO bug)
```json
{
    "result": [
        {
            "name": "Smartphones",
            "catalogueId": 1,
            "items": [
                {
                    "name": "Smartphone Y",
                    "itemId": 104,
                    "brand": "BrandY",
                    "type": "Electronics",
                    "description": "top-notch smartphone"
                }
            ]
        },
        {
            "name": "Laptops",
            "catalogueId": 1,
            "items": [
                {
                    "name": "Tablet Pro",
                    "itemId": 102,
                    "brand": "BrandG",
                    "type": "Electronics",
                    "description": "advanced tablet for professionals"
                },
                {
                    "name": "Smartwatch Series Z",
                    "itemId": 103,
                    "brand": "BrandH",
                    "type": "Electronics",
                    "description": "feature-rich smartwatch"
                },
                {
                    "name": "Laptop Model X",
                    "itemId": 101,
                    "brand": "BrandX",
                    "type": "Electronics",
                    "description": "high-performance laptop"
                }
            ]
        }
    ]
}
```

#### get /api/v1/search/by?text=Electronics [cookies regionId=0]
Same response on not existing regionId

[//]: # (TODO bug)
```json
{
  "result": [
    {
      "name": "Smartphones",
      "catalogueId": 1,
      "items": [
        {
          "name": "Smartphone Y",
          "itemId": 104,
          "brand": "BrandY",
          "type": "Electronics",
          "description": "top-notch smartphone"
        }
      ]
    },
    {
      "name": "Laptops",
      "catalogueId": 1,
      "items": [
        {
          "name": "Tablet Pro",
          "itemId": 102,
          "brand": "BrandG",
          "type": "Electronics",
          "description": "advanced tablet for professionals"
        },
        {
          "name": "Smartwatch Series Z",
          "itemId": 103,
          "brand": "BrandH",
          "type": "Electronics",
          "description": "feature-rich smartwatch"
        },
        {
          "name": "Laptop Model X",
          "itemId": 101,
          "brand": "BrandX",
          "type": "Electronics",
          "description": "high-performance laptop"
        }
      ]
    }
  ]
}
```

#### get /api/v1/search/by?text=Elctrincs
Typo in catalogue 
```json
{
  "result": []
}
```

#### get /api/v1/search/by?text=что
что is a cyrillic word
```json
{
  "result": []
}
```

#### get /api/v1/search/by?text=что-то
что-то is a cyrillic word with dash

result is all items with dash in it

[//]: # (TODO bug)
```json
{
  "result": [
    {
      "name": "Smartphones",
      "catalogueId": 1,
      "items": [
        {
          "name": "Smartphone Y",
          "itemId": 104,
          "brand": "BrandY",
          "type": "Electronics",
          "description": "top-notch smartphone"
        }
      ]
    },
    {
      "name": "Laptops",
      "catalogueId": 1,
      "items": [
        {
          "name": "Smartwatch Series Z",
          "itemId": 103,
          "brand": "BrandH",
          "type": "Electronics",
          "description": "feature-rich smartwatch"
        },
        {
          "name": "Laptop Model X",
          "itemId": 101,
          "brand": "BrandX",
          "type": "Electronics",
          "description": "high-performance laptop"
        }
      ]
    },
    {
      "name": "T-Shirts",
      "catalogueId": 2,
      "items": [
        {
          "name": "Casual T-Shirt",
          "itemId": 105,
          "brand": "BrandZ",
          "type": "Clothing",
          "description": "comfortable cotton t-shirt"
        },
        {
          "name": "Formal Shirt",
          "itemId": 106,
          "brand": "BrandI",
          "type": "Clothing",
          "description": "elegant formal shirt"
        },
        {
          "name": "Denim Jeans",
          "itemId": 107,
          "brand": "BrandJ",
          "type": "Clothing",
          "description": "classic denim jeans"
        }
      ]
    }
  ]
}
```