<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Document extends Model
{
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $morphClass = 'App\Models\Document'; 
    protected $fillable = [
        'url', 'documentable_id', 'documentable_type',
    ];

    public function documentable(){
        return $this->morphTo();
    }
}
