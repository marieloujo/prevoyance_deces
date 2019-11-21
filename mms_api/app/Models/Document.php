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
        'url', 'documenteable_id', 'documenteable_type',
    ];

    public function documenteable(){
        return $this->morphTo();
    }
}
